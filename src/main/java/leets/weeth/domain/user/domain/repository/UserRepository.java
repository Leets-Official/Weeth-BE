package leets.weeth.domain.user.domain.repository;

import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    List<User> findAllByOrderByNameAsc();

    @Query("SELECT uc.user FROM UserCardinal uc WHERE uc.cardinal = :cardinal AND uc.user.status = :status")
    List<User> findAllByCardinalAndStatus(@Param("cardinal") Cardinal cardinal, @Param("status") Status status);

    /*
    todo 차후 리팩토링
     */
    @Query("""
                SELECT u FROM User u
                JOIN UserCardinal uc ON uc.user.id = u.id
                JOIN Cardinal c ON c.id = uc.cardinal.id
                WHERE u.status = :status
                AND c.cardinalNumber = (
                    SELECT MAX(subC.cardinalNumber)
                    FROM Cardinal subC
                    JOIN UserCardinal subUc ON subUc.cardinal.id = subC.id
                    WHERE subUc.user.id = u.id
                )
                ORDER BY u.name ASC
            """)
    Slice<User> findAllByStatusOrderedByCardinalAndName(@Param("status") Status status, Pageable pageable);
}
