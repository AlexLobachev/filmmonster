package ru.nexo.ocenka.filmmonster.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.db.UserDao;
import ru.nexo.ocenka.filmmonster.exception.NotFound;
import ru.nexo.ocenka.filmmonster.model.User;

import java.util.List;

@Slf4j
@Component("userStorage")
public class InMemoryUserStorage implements UserStorage {
    @Qualifier("userDao")
    private final UserDao userDao;

    @Autowired
    InMemoryUserStorage(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(User user) {
        user = create(user);
        return create(userDao.createUser(user));
    }

    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    public User getUser(int idUser) {

        try {
            return userDao.getUser(idUser);
        }
        catch (EmptyResultDataAccessException e){
            throw new NotFound("Пользователь не найден");
        }

    }

    public List<User> getAllUser() {
        return userDao.getAllUser();

    }
    @Override
    public void deleteUser(int idUser){
        userDao.deleteUser(idUser);
    }

    private User create(User user) {
        if (user.getName().isBlank()) {
            user = User.builder()
                    .id(user.getId())
                    .name(user.getLogin())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .build();
            log.debug("Пользователь не ввел имя, будет задействован логин {}", user.getLogin());
        } else {
            user = User.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .login(user.getLogin())
                    .build();
        }
        return user;
    }

}
