package ru.nexo.ocenka.filmmonster.service;

import ru.nexo.ocenka.filmmonster.model.User;

import java.util.List;

public interface UserService {
    User addFriend(int idUser, int idFriend);

    User deleteFriend(int idUser, int idFriend);

    List<User> getAllFriend(int id);

    List<User> getCommonFriends(int idUser, int idFriend);
    User confirmYourFriendRequest(int idUser, int idFriend);
}
