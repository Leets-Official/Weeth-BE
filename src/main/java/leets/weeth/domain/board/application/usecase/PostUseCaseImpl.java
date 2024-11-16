package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.exception.PostNotFoundException;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.PostDeleteService;
import leets.weeth.domain.board.domain.service.PostFindService;
import leets.weeth.domain.board.domain.service.PostSaveService;
import leets.weeth.domain.board.domain.service.PostUpdateService;
import leets.weeth.domain.file.application.dto.request.FileUpdateRequest;
import leets.weeth.domain.file.application.dto.response.FileResponse;
import leets.weeth.domain.file.application.mapper.FileMapper;
import leets.weeth.domain.file.domain.entity.File;
import leets.weeth.domain.file.domain.service.FileGetService;
import leets.weeth.domain.file.domain.service.FileSaveService;
import leets.weeth.domain.file.domain.service.FileUpdateService;
import leets.weeth.domain.user.application.exception.UserNotMatchException;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private final PostMapper mapper;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public void save(PostDTO.Save request, Long userId) {
        User user = userGetService.find(userId);

        Post post = mapper.fromPostDto(request, user);
        postSaveService.save(post);

        List<File> files = request.files().stream()
                .map(fileSaveRequest -> fileMapper.toFile(fileSaveRequest.fileName(), fileSaveRequest.fileUrl(), post))
                .toList();

        fileSaveService.save(files);
    }

    @Override
    public PostDTO.Response findPost(Long postId) {
        Post post = postFindService.find(postId);

        List<FileResponse> response = fileGetService.findAllByPost(postId).stream()
                .map(fileMapper::toFileResponse)
                .toList();

        return mapper.toPostDto(post, response);
    }

    @Override
    public List<PostDTO.ResponseAll> findPosts(Long postId, Integer count) {

        Long finalPostId = postFindService.findFinalPostId();

        // 첫번째 요청인 경우
        if (postId == null) {
            postId = finalPostId + 1;
        }

        // postId가 1 이하이거나 최대값보다 클경우
        if (postId < 1 || postId > finalPostId + 1) {
            throw new PostNotFoundException();
        }

        Pageable pageable = PageRequest.of(0, count); // 첫 페이지, 페이지당 15개 게시글

        List<Post> posts = postFindService.findRecentPosts(postId, pageable);

        return posts.stream()
                .map(mapper::toAll)
                .toList();
    }

    @Override
    @Transactional
    public void update(Long postId, PostDTO.Update dto, Long userId) {
        Post post = validateOwner(postId, userId);

        List<File> fileList = fileGetService.findAllByPost(postId);

        fileUpdateService.updateFiles(fileList, dto.files());

        postUpdateService.update(post, dto);
    }

    @Override
    @Transactional
    public void delete(Long postId, Long userId) {
        validateOwner(postId, userId);
        postDeleteService.delete(postId);
    }

    private Post validateOwner(Long postId, Long userId) {
        Post post = postFindService.find(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return post;
    }

}
