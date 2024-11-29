package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.exception.PostNotFoundException;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.PostDeleteService;
import leets.weeth.domain.board.domain.service.PostFindService;
import leets.weeth.domain.board.domain.service.PostSaveService;
import leets.weeth.domain.board.domain.service.PostUpdateService;
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

        return mapper.toPostDto(post, response);
    }

    @Override
    public Slice<PostDTO.ResponseAll> findPosts(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Post> recentPosts = postFindService.findRecentPosts(pageable);

        return recentPosts.map(post -> mapper.toAll(post));
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

}
