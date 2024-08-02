package leets.weeth.domain.board.domain.entity;

import jakarta.persistence.*;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    public Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    // List<Comment>
    // List<String> urls

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
