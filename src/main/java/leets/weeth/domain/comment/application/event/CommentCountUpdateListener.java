package leets.weeth.domain.comment.application.event;

import jakarta.persistence.EntityManager;
import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CommentCountUpdateListener {

    private final EntityManager entityManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleNoticeCommentDeleted(NoticeCommentCountUpdateEvent event) {
        Notice notice = entityManager.merge(event.getNotice());
        notice.updateCommentCount();

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePostCommentDeleted(PostCommentCountUpdateEvent event) {
        Post post = entityManager.merge(event.getPost());
        post.updateCommentCount();
    }

}