package ru.nexo.ocenka.filmmonster.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.nexo.ocenka.filmmonster.db.UserDao;
import ru.nexo.ocenka.filmmonster.model.User;

import java.util.List;
import java.util.Map;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {
    @Qualifier("userDao")
    private final UserDao userDao;


    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Map<String, Integer> addFriend(int idUser, int idFriend) {
        return userDao.addFriend(idUser, idFriend);
    }

    @Override
    public void deleteFriend(int idUser, int idFriend) {
        userDao.deleteFriend(idUser, idFriend);
        log.debug("Друг с ID = {} удален", idFriend);

    }

    @Override
    public List<User> getAllFriend(int idUser) {
        return userDao.getAllFriend(idUser);
    }

    @Override
    public List<User> getCommonFriends(int idUser, int idFriend) {
        return userDao.getCommonFriends(idUser, idFriend);
    }

    @Override
    public void confirmYourFriendRequest(int idUser, int idFriend) {
        if (userDao.getAllFriend(idUser).stream().anyMatch(x -> x.getId().equals(idFriend))) {
            log.debug("Друг с ID = {} уже у вас в друзьях", idFriend);
        }
        userDao.confirmYourFriendRequest(idUser, idFriend);

    }


}
