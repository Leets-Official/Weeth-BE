package leets.weeth.domain.board.domain.repository;

import leets.weeth.domain.board.domain.entity.Notice;
import leets.weeth.domain.board.domain.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT MAX(p.id) FROM Post p")
    Long findLastId();

    @Query("SELECT p FROM Post p WHERE p.id < :maxPostId ORDER BY p.id DESC")
    List<Post> findRecentPosts(@Param("maxPostId") Long maxPostId, Pageable pageable);
}
