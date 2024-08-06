package leets.weeth.domain.board.domain.repository;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT MAX(n.id) FROM Notice n")
    Long findLastId();

    @Query("SELECT n FROM Notice n WHERE n.id < :maxNoticeId ORDER BY n.id DESC")
    List<Notice> findRecentNotices(@Param("maxNoticeId") Long maxNoticeId, Pageable pageable);

}
