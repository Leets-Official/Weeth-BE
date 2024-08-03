package leets.weeth.domain.board.domain.repository;

import leets.weeth.domain.board.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
