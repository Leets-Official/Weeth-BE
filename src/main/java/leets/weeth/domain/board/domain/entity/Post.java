package leets.weeth.domain.board.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Post extends Board {

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    @PreUpdate
    public void updateCommentCount() {
        this.updateCommentCount(this.comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void update(PostDTO.Update dto, List<String> fileUrls) {
        this.updateUpperClass(dto, fileUrls);
    }

}
