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
import leets.weeth.global.common.error.exception.custom.UserNotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostUseCaseImpl implements PostUsecase{

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
        postSaveService.save(mapper.from(request, fileUrls, user));
    }

    /*
    게시글 수정 시 파일이 넘어오지 않으면, 파일이 제거됨.
    수정할 때도 파일을 같이 넘기면, 또 업로드가 발생함 -> 근데 동일한 파일은 재 업로드가 아닌거 같기도 함.
    -> 테스트 해보기
     */
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
