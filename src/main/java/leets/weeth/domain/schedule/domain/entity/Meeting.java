package leets.weeth.domain.schedule.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.schedule.domain.entity.enums.MeetingStatus;
import leets.weeth.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Meeting extends Schedule {

    private Integer weekNumber;

    private Integer cardinal;

    private Integer code;

    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    public void update(MeetingDTO.Update dto, User user) {
        this.updateUpperClass(dto, user);
        this.weekNumber = dto.weekNumber();
        this.cardinal = dto.cardinal();
    }

    @PrePersist
    public void init() {
        this.meetingStatus = MeetingStatus.OPEN;
    }

    public void close() {
        this.meetingStatus = MeetingStatus.CLOSE;
    }
}
