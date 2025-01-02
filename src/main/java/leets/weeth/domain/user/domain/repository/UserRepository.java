package leets.weeth.domain.user.domain.repository;

import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(long kakaoId);

    boolean existsByEmail(String email);
    boolean existsByStudentId(String studentId);
    boolean existsByTel(String tel);

    boolean existsByStudentIdAndIdIsNot(String studentId, Long id);
    boolean existsByTelAndIdIsNot(String tel, Long id);

    List<User> findAllByStatusOrderByName(Status status);

    @Query(value = "SELECT * FROM users u WHERE JSON_CONTAINS(u.cardinals, CAST(:cardinal AS JSON), '$')", nativeQuery = true)
    List<User> findByCardinal(@Param("cardinal") int cardinal);
}
