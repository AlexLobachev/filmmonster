package ru.nexo.ocenka.filmmonster.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexo.ocenka.filmmonster.model.Friend;
import ru.nexo.ocenka.filmmonster.model.User;
import ru.nexo.ocenka.filmmonster.storage.UserStorage;

import java.util.*;
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
        if (userStorage.getUser(idUser).getFriends().stream().anyMatch((x -> x.getIdFriend() == idFriend))) {
            log.debug("Друг с ID = {} уже у вас в друзьях", userStorage.getUser(idFriend).getId());
        } else {
            userStorage.getUser(idUser).getFriends().add(new Friend(idFriend));

        }
        return userStorage.getUser(idUser);
    }

    public User deleteFriend(int idUser, int idFriend) {
        userStorage.getUser(idUser).getFriends().removeIf(x -> x.getIdFriend() == idFriend);
        log.debug("Друг с ID = {} удален", idFriend);
        return userStorage.getUser(idUser);
    }

    public List<User> getAllFriend(int id) {
        List<Integer> idFriends = userStorage.getUser(id)
                .getFriends().stream()
                .filter(x -> x.getFriendship_status() == 1).map(Friend::getIdFriend).toList();


        return userStorage.getAllUser().stream().filter(x -> idFriends.contains(x.getId())).collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int idUser, int idFriend) {
        List<Friend> friends = new ArrayList<>();
        Set<Integer> commonFriends = new HashSet<>();
        friends.addAll(getFriends(idUser));
        friends.addAll(getFriends(idFriend));
        for (int i = 0; i < friends.size(); i++) {
            for (int j = i + 1; j < friends.size(); j++) {
                if (friends.get(i).getIdFriend().equals(friends.get(j).getIdFriend())) {
                    commonFriends.add(friends.get(i).getIdFriend());
                }
            }
        }
        return userStorage.getAllUser().stream().filter(x -> commonFriends.contains(x.getId())).collect(Collectors.toList());
    }

    public User confirmYourFriendRequest(int idUser, int idFriend) {
        Objects.requireNonNull(userStorage.getUser(idUser).getFriends().stream().filter(x -> x.getIdFriend().equals(idFriend)).findFirst().orElse(null)).setFriendship_status(1);
        log.debug("Друг с ID = {} добавлен, теперь у вас {} друзей", userStorage.getUser(idFriend).getId(),
                userStorage.getUser(idUser).getFriends().size());
        addFriend(idFriend, idUser);
        Objects.requireNonNull(userStorage.getUser(idFriend).getFriends().stream().filter(x -> x.getIdFriend().equals(idUser)).findFirst().orElse(null)).setFriendship_status(1);
        return userStorage.getUser(idUser);
    }

    private List<Friend> getFriends(int user) {
        return userStorage.getUser(user).getFriends().stream().filter(x -> x.getFriendship_status() == 1).toList();
    }
}
