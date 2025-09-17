package ru.nexo.ocenka.filmmonster.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.exception.NotFound;
import ru.nexo.ocenka.filmmonster.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> userMap = new HashMap<>();
    private int id = 0;

    public User createUser(User user) {
        user = create(user);
        userMap.put(id, user);
        return user;
    }

    public User updateUser(User user) {
        user = create(user);
        userMap.put(user.getId(), user);
        return user;
    }

    public User getUser(int id) {
        return getAllUser().stream().filter(x->x.getId()==id).findFirst().orElseThrow(()->new NotFound("Пользователь не найден"));
    }

    public List<User> getAllUser() {
        return new ArrayList<>(userMap.values());
    }

    private User create(User user) {
        if (user.getName().isBlank()) {
            user = User.builder()
                    .name(user.getLogin())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .id(user.getId())
                    .login(user.getLogin())
                    .build();
            log.debug("Пользователь не ввел имя, будет задействован логин {}", user.getLogin());
            //return user;
        }
        if (user.getId() == null) {
            user = User.builder()
                    .name(user.getName())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .id(generateId())
                    .login(user.getLogin())
                    .build();
        }
        return user;
    }

    private int generateId() {
        return ++id;
    }
}
