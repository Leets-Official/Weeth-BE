package leets.weeth.domain.board.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import leets.weeth.domain.board.application.dto.PostDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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


    public void addComment(Comment newComment) {
        this.comments.add(newComment);
    }

    public void update(PostDTO.Update dto, List<String> fileUrls) {
        this.updateUpperClass(dto, fileUrls);
    }

    public void recalculateCommentCount() {
        int count = 0;
        for (Comment comment : getComments()) {
            if (!comment.getIsDeleted()) {
                count++;
                count += comment.getChildren().stream().filter(child -> !child.getIsDeleted()).count();
            }
        }
        this.commentCount = count;
    }

}
