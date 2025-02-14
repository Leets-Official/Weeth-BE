package leets.weeth.domain.user.domain.repository;

import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.UserCardinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCardinalRepository extends JpaRepository<UserCardinal, Long> {

    List<UserCardinal> findAllByUser(User user);

    @Query("SELECT uc FROM UserCardinal uc WHERE uc.user IN :users")
    List<UserCardinal> findAllByUsers(List<User> users);

	List<UserCardinal> findAllByOrderByUser_NameAsc();
}
