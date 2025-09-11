package ru.nexo.ocenka.filmmonster.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nexo.ocenka.filmmonster.exception.ValidationExceptions;
import ru.nexo.ocenka.filmmonster.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> userMap = new HashMap<>();
    private int id = 0;

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        try {
            if (user.getLogin().contains(" "))
                throw new ValidationExceptions("Логин не может содержать пробелы");
        } catch (ValidationExceptions e) {
            log.debug(e.getMessage());
        }

        user = create(user);

        userMap.put(id, user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        try {
            if (!userMap.containsKey(user.getId()))
                throw new ValidationExceptions("Пользователь не зарегистрирован");
        } catch (ValidationExceptions e) {
            log.debug(e.getMessage());
        }
        user = create(user);
        userMap.put(user.getId(), user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        return new ArrayList<>(userMap.values());
    }


    private User create(User user) {
        if (user.getName() == null) {
            user = User.builder()
                    .name(user.getLogin())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .id(user.getId())
                    .login(user.getLogin())
                    .build();
            log.debug("Пользователь не ввел имя, будет задействован логин {}", user.getLogin());
            return user;
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
