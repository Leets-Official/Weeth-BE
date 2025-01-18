package leets.weeth.domain.user.domain.service;

import leets.weeth.domain.user.application.exception.CardinalNotFoundException;
import leets.weeth.domain.user.domain.entity.Cardinal;
import leets.weeth.domain.user.domain.entity.User;
import leets.weeth.domain.user.domain.entity.UserCardinal;
import leets.weeth.domain.user.domain.repository.UserCardinalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCardinalGetService {

    private final UserCardinalRepository userCardinalRepository;

    public List<UserCardinal> getUserCardinals(User user) {
        return userCardinalRepository.findAllByUser(user);
    }

    public List<UserCardinal> findAll() {
        return userCardinalRepository.findAll();
    }

    public List<UserCardinal> findAll(List<User> users) {
        return userCardinalRepository.findAllByUsers(users);
    }

    public boolean notContains(User user, Cardinal cardinal) {
        return getUserCardinals(user).stream()
                .noneMatch(userCardinal -> userCardinal.getCardinal().equals(cardinal));
    }

    public boolean isCurrent(User user, Cardinal cardinal) {
        Integer maxCardinalNumber = getUserCardinals(user).stream()
                .map(UserCardinal::getCardinal)
                .map(Cardinal::getCardinalNumber)
                .max(Integer::compareTo)
                .orElseThrow(CardinalNotFoundException::new);

        return maxCardinalNumber < cardinal.getCardinalNumber();
    }

    public Cardinal getCurrentCardinal(User user) {
        return getUserCardinals(user).stream()
                .map(UserCardinal::getCardinal)
                .max(Comparator.comparing(Cardinal::getCardinalNumber))
                .orElseThrow(CardinalNotFoundException::new);
    }
}
