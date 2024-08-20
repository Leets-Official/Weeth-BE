package leets.weeth.domain.board.application.usecase;

import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.board.application.mapper.PostMapper;
import leets.weeth.domain.board.domain.entity.Post;
import leets.weeth.domain.board.domain.service.PostDeleteService;
import leets.weeth.domain.board.domain.service.PostFindService;
import leets.weeth.domain.board.domain.service.PostSaveService;
import leets.weeth.domain.board.domain.service.PostUpdateService;
import leets.weeth.domain.file.service.FileSaveService;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.service.UserGetService;
import leets.weeth.global.common.error.exception.custom.LastPostFoundException;
import leets.weeth.global.common.error.exception.custom.PostNotFoundException;
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostUseCaseImpl implements PostUsecase {

    private final PostSaveService postSaveService;
    private final PostFindService postFindService;
    private final PostUpdateService postUpdateService;
    private final PostDeleteService postDeleteService;

    private final UserGetService userGetService;
    private final FileSaveService fileSaveService;

    private final PostMapper mapper;

    @Override
    public void save(PostDTO.Save request, List<MultipartFile> files, Long userId) {
        User user = userGetService.find(userId);

        List<String> fileUrls;
        fileUrls = fileSaveService.uploadFiles(files);
        postSaveService.save(mapper.fromPostDto(request, fileUrls, user));
    }

    @Override
    public PostDTO.Response findPost(Long postId) {
        Post post = postFindService.find(postId);
        return mapper.toPostDto(post);
    }

    @Override
    public List<PostDTO.ResponseAll> findPosts(Long postId, Integer count) throws LastPostFoundException {

        Long finalPostId = postFindService.findFinalPostId();
        Long firstPostId = postFindService.findFirstPostId();

        // 첫 번째 요청인 경우
        if (postId == null) {
            postId = finalPostId + 1;
        }

        // postId가 잘못된 경우
        if (postId < 1 || postId > finalPostId + 1) {
            throw new PostNotFoundException();
        }

        // 마지막 게시글인 경우 예외 처리
        if (postId.equals(firstPostId)) {
            throw new LastPostFoundException();
        }

        Pageable pageable = PageRequest.of(0, count); // 첫 페이지, 페이지당 15개 게시글

        List<Post> posts = postFindService.findRecentPosts(postId, pageable);

        return posts.stream()
                .map(mapper::toAll)
                .toList();
    }

    @Override
    public void update(Long postId, PostDTO.Update dto, List<MultipartFile> files, Long userId) throws UserNotMatchException {
        Post post = validateOwner(postId, userId);

        List<String> fileUrls;
        fileUrls = fileSaveService.uploadFiles(files);

        postUpdateService.update(post, dto, fileUrls);
    }

    @Override
    public void delete(Long postId, Long userId) throws UserNotMatchException {
        validateOwner(postId, userId);
        postDeleteService.delete(postId);
    }

    private Post validateOwner(Long postId, Long userId) throws UserNotMatchException {
        Post post = postFindService.find(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new UserNotMatchException();
        }
        return post;
    }

}
