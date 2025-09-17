package ru.nexo.ocenka.filmmonster.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexo.ocenka.filmmonster.model.User;
import ru.nexo.ocenka.filmmonster.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(int idUser, int idFriend) {
        if (userStorage.getUser(idUser).getFriends().contains(idFriend)) {
            log.debug("Друг с ID = {} уже у вас в друзьях", userStorage.getUser(idFriend).getId());
        } else {
            userStorage.getUser(idUser).getFriends().add(idFriend);
            userStorage.getUser(idFriend).getFriends().add(idUser);
            log.debug("Друг с ID = {} добавлен, теперь у вас {} друзей", userStorage.getUser(idFriend).getId(),
                    userStorage.getUser(idUser).getFriends().size());
        }
        return userStorage.getUser(idUser);
    }

    public User deleteFriend(int idUser, int idFriend) {
        userStorage.getUser(idUser).getFriends().remove(idFriend);
        log.debug("Друг с ID = {} удален", idFriend);
        return userStorage.getUser(idUser);
    }

    public List<User> getAllFriend(int id) {
        List<Integer> idFriends = new ArrayList<>(userStorage.getUser(id).getFriends());
        return userStorage.getAllUser().stream().filter(x -> idFriends.contains(x.getId())).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int idUser, int idFriend) {
        List<Integer> commonFriends = new ArrayList<>();
        userStorage.getUser(idUser)
                .getFriends()
                .stream()
                .filter(x -> userStorage.getUser(idFriend).getFriends().contains(x))
                .collect(Collectors.toCollection(() -> commonFriends));
        return userStorage.getAllUser().stream().filter(x -> commonFriends.contains(x.getId())).collect(Collectors.toList());
    }
}
