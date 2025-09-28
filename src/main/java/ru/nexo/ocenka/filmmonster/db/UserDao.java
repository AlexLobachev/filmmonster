package ru.nexo.ocenka.filmmonster.db;

import ru.nexo.ocenka.filmmonster.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUser();

    User getUser(int idUser);

    Map<String,Integer> addFriend(int idUser, int idFriend);

    void deleteFriend(int idUser, int idFriend);

    List<User> getAllFriend(int idUser);

    List<User> getCommonFriends(int idUser, int idFriend);

    void confirmYourFriendRequest(int idUser, int idFriend);

    void deleteUser(int idUser);
}
