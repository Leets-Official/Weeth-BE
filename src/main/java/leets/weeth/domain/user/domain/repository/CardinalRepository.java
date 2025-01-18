package leets.weeth.domain.user.domain.repository;

import leets.weeth.domain.user.domain.entity.Cardinal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardinalRepository extends JpaRepository<Cardinal, Long> {

    Optional<Cardinal> findByCardinalNumber(Integer cardinal);
}
