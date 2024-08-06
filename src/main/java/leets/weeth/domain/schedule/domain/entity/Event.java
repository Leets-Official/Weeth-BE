package leets.weeth.domain.schedule.domain.entity;

import jakarta.persistence.Entity;
import leets.weeth.domain.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static leets.weeth.domain.schedule.application.dto.EventDTO.Update;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Event extends Schedule {

    private String requiredItem;

    private String memberCount;

    public void update(Update dto, User user) {
        this.updateUpperClass(dto, user);
        this.requiredItem = dto.requiredItem();
    }
}
