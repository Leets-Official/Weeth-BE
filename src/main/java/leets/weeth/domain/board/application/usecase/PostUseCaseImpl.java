package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.exception.PageNotFoundException;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.PostDeleteService;
import leets.weeth.domain.board.domain.service.PostFindService;
import leets.weeth.domain.board.domain.service.PostSaveService;
import leets.weeth.domain.board.domain.service.PostUpdateService;
import leets.weeth.domain.comment.application.dto.CommentDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.application.mapper.FileMapper;
import leets.weeth.domain.file.domain.entity.File;
import leets.weeth.domain.file.domain.service.FileDeleteService;
import leets.weeth.domain.file.domain.service.FileGetService;
import leets.weeth.domain.file.domain.service.FileSaveService;
import leets.weeth.domain.file.domain.service.FileUpdateService;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostUseCaseImpl implements PostUsecase {

    private final PostSaveService postSaveService;
    private final PostFindService postFindService;
    private final PostUpdateService postUpdateService;
    private final PostDeleteService postDeleteService;

    private final UserGetService userGetService;

    private final FileSaveService fileSaveService;
    private final FileGetService fileGetService;
    private final FileUpdateService fileUpdateService;
    private final FileDeleteService fileDeleteService;

    private final PostMapper mapper;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public void save(PostDTO.Save request, Long userId) {
        User user = userGetService.find(userId);

        Post post = mapper.fromPostDto(request, user);
        postSaveService.save(post);

        List<File> files = fileMapper.toFileList(request.files(), post);
        fileSaveService.save(files);
    }

    @Override
    public PostDTO.Response findPost(Long postId) {
        Post post = postFindService.find(postId);

        List<FileResponse> response = getFiles(postId).stream()
                .map(fileMapper::toFileResponse)
                .toList();


        return mapper.toPostDto(post, response, filterParentComments(post.getComments()));
    }

    @Override
    public Slice<PostDTO.ResponseAll> findPosts(int pageNumber, int pageSize) {
        if (pageNumber < 0) {
            throw new PageNotFoundException();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Post> posts = postFindService.findRecentPosts(pageable);

        return posts.map(post->mapper.toAll(post, checkFileExistsByPost(post.id)));
    }

    @Override
    @Transactional
    public void update(Long postId, PostDTO.Update dto, Long userId) {
        Post post = validateOwner(postId, userId);

        List<File> fileList = getFiles(postId);
        fileDeleteService.delete(fileList);

        List<File> files = fileMapper.toFileList(dto.files(), post);
        fileSaveService.save(files);

        postUpdateService.update(post, dto);
    }

    @Override
    @Transactional
    public void delete(Long postId, Long userId) {
        validateOwner(postId, userId);

        List<File> fileList = getFiles(postId);
        fileDeleteService.delete(fileList);

        postDeleteService.delete(postId);
    }

    private List<File> getFiles(Long postId) {
        return fileGetService.findAllByPost(postId);
    }

    private Post validateOwner(Long postId, Long userId) {
        Post post = postFindService.find(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return post;
    }

    public boolean checkFileExistsByPost(Long postId){
        return !fileGetService.findAllByPost(postId).isEmpty();
    }

    private List<CommentDTO.Response> filterParentComments(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        // 부모 댓글만 필터링하고, 각 부모 댓글에 대해 자식 댓글을 매핑
        return comments.stream()
            .filter(comment -> comment.getParent() == null) // 부모 댓글만 필터링
            .map(this::mapCommentWithChildren) // 자식 댓글 포함하여 매핑
            .toList();
    }

    private CommentDTO.Response mapCommentWithChildren(Comment comment) {
        if (comment == null) {
            return null;
        }

        // 기본 댓글 정보 매핑
        CommentDTO.Response.ResponseBuilder response = CommentDTO.Response.builder();

        response.name(comment.getUser().getName());
        response.time(comment.getModifiedAt());
        response.id(comment.getId());
        response.content(comment.getContent());

        // 자식 댓글들을 재귀적으로 매핑하여 children 필드에 설정
        List<CommentDTO.Response> childrenResponses = comment.getChildren().stream()
            .map(this::mapCommentWithChildren) // 자식 댓글도 동일하게 처리
            .collect(Collectors.toList());
        response.children(childrenResponses);

        return response.build();
    }

}
