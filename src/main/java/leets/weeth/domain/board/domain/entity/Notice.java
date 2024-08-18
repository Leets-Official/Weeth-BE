package leets.weeth.domain.board.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import leets.weeth.domain.board.application.dto.NoticeDTO;
import leets.weeth.domain.comment.domain.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Slf4j
public class Notice extends Board {

    @OneToMany(mappedBy = "notice", orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    public void updateCommentCount() {
        log.info("notice.updateCommentCount");
        this.updateCommentCount(this.comments);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        this.increaseCommentCount();
    }

    public void update(NoticeDTO.Update dto, List<String> fileUrls){
        this.updateUpperClass(dto, fileUrls);
    }

}
