package leets.weeth.domain.schedule.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import leets.weeth.domain.attendance.domain.entity.Attendance;
import leets.weeth.domain.schedule.application.dto.MeetingDTO;
import leets.weeth.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Meeting extends Schedule {

    private Integer weekNumber;

    private Integer cardinal;

    private Integer code;

    @OneToMany(mappedBy = "meeting")
    private List<Attendance> attendances = new ArrayList<>();

    public void update(MeetingDTO.Update dto, User user) {
        this.updateUpperClass(dto, user);
        this.weekNumber = dto.weekNumber();
        this.cardinal = dto.cardinal();
    }

    public void add(Attendance attendance) {
        this.attendances.add(attendance);
    }
}
