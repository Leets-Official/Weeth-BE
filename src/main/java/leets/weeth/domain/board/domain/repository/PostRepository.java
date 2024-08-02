package leets.weeth.domain.board.domain.repository;

import leets.weeth.domain.board.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
