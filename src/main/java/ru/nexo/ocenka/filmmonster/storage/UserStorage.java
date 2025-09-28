package ru.nexo.ocenka.filmmonster.storage;

import ru.nexo.ocenka.filmmonster.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUser();
    User getUser(int idUser);
    void deleteUser(int idUser);
}
