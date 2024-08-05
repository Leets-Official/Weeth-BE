package leets.weeth.domain.penalty.domain.repository;

import leets.weeth.domain.penalty.domain.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    List<Penalty> findByUserId(Long userId);

}
