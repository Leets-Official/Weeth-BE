package leets.weeth.domain.schedule.domain.entity;

import jakarta.persistence.Entity;
import leets.weeth.domain.schedule.application.dto.MeetingDTO;
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

    public void update(MeetingDTO.Update dto, User user) {
        this.updateUpperClass(dto, user);
        this.weekNumber = dto.weekNumber();
        this.cardinal = dto.cardinal();
    }


}
