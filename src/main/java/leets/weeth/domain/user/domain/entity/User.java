package leets.weeth.domain.user.domain.entity;

import jakarta.persistence.*;
import leets.weeth.domain.user.application.converter.CardinalListConverter;
import leets.weeth.domain.user.application.dto.UserDTO;
import leets.weeth.domain.user.domain.entity.enums.Department;
import leets.weeth.domain.user.domain.entity.enums.Position;
import leets.weeth.domain.user.domain.entity.enums.Role;
import leets.weeth.domain.user.domain.entity.enums.Status;
import leets.weeth.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String password;

    private String studentId;

    private String tel;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Convert(converter = CardinalListConverter.class)
    private List<Integer> cardinals;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    private Integer attendanceCount;

    private Integer attendanceRate;

    @PrePersist
    public void init() {
        status = Status.WAITING;
        role = Role.USER;
        attendanceCount = 0;
        attendanceRate = 0;
    }

    public void updateRefreshToken(String updatedToken) {
        this.refreshToken = updatedToken;
    }

    public void leave() {
        this.status = Status.LEFT;
    }

    public void applyOB(Integer cardinal) {
        this.cardinals.add(cardinal);
    }

    public boolean isInactive() {
        return this.status != Status.ACTIVE;
    }

    public void update(UserDTO.Update dto, PasswordEncoder passwordEncoder) {
        this.name = dto.name();
        this.email = dto.email();
        this.password = passwordEncoder.encode(dto.password());
        this.studentId = dto.studentId();
        this.tel = dto.tel();
        this.department = Department.to(dto.department());
    }

    public void accept() {
        this.status = Status.ACTIVE;
    }

    public void ban() {
        this.status = Status.BANNED;
    }

    public void update(String role) {
        this.role = Role.valueOf(role);
    }

    public Integer getCurrentCardinal() {
        return this.cardinals.stream().max(Integer::compareTo)
                .orElse(0);
    }

    public boolean notContains(Integer cardinal) {
        return !this.cardinals.contains(cardinal);
    }

    public void reset(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(studentId);
    }
}
