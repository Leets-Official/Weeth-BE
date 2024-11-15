package leets.weeth.domain.file.domain.entity;

import jakarta.persistence.*;
import leets.weeth.domain.board.domain.entity.Post;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String fileName;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
