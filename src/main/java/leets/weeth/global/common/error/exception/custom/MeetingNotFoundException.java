package leets.weeth.global.common.error.exception.custom;

import jakarta.persistence.EntityNotFoundException;

public class MeetingNotFoundException extends EntityNotFoundException {
    public MeetingNotFoundException() {super("존재하지 않는 정기 모임입니다.");}
}
