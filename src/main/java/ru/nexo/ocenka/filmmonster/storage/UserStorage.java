package ru.nexo.ocenka.filmmonster.storage;

import ru.nexo.ocenka.filmmonster.model.User;

import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUser();
    User getUser(int id);
}
