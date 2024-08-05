package leets.weeth.domain.account.domain.entity;

import jakarta.persistence.*;
import leets.weeth.domain.file.converter.FileListConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer price;

    private LocalDate date;

    @Convert(converter = FileListConverter.class)
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
