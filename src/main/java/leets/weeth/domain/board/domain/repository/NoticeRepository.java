package leets.weeth.domain.board.domain.repository;

import leets.weeth.domain.board.domain.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Slice<Notice> findPageBy(Pageable page);

}
