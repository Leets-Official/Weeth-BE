package leets.weeth.domain.schedule.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class EventNotFoundException extends EntityNotFoundException {
    public EventNotFoundException() {
        super("존재하지 않는 일정입니다.");
    }
}