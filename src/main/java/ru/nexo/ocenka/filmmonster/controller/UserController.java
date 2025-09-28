package ru.nexo.ocenka.filmmonster.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.nexo.ocenka.filmmonster.model.User;
import ru.nexo.ocenka.filmmonster.service.UserService;
import ru.nexo.ocenka.filmmonster.storage.UserStorage;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Qualifier("userStorage")
    private final UserStorage userStorage;
    @Qualifier("userService")
    private final UserService userService;

    @Autowired
    UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        userStorage.getUser(user.getId());
        return userStorage.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userStorage.getAllUser();
    }

    @GetMapping("/users/{idUser}")
    public User getUser(@PathVariable int idUser) {

        return userStorage.getUser(idUser);

    }

    @DeleteMapping("/users/{idUser}")
    public void deleteUser(@PathVariable("idUser") int userId) {
        userStorage.deleteUser(userId);
    }


    @PutMapping("/users/{id}/friends/{friendId}")
    public Map<String, Integer> addFriend(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriend) {
        if (idUser == idFriend) {
            throw new ValidationException("Попытка добавить в друзья самого себя");
        }
        userStorage.getUser(idFriend);
        return userService.addFriend(idUser, idFriend);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriend) {
        userStorage.getUser(idFriend);
        userService.deleteFriend(idUser, idFriend);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getAllFriend(@PathVariable int id) {
        return userService.getAllFriend(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") int idUser, @PathVariable("otherId") int idFriend) {
        userStorage.getUser(idFriend);
        return userService.getCommonFriends(idUser, idFriend);
    }

    @PutMapping("/users/{id}/friend/{friendId}")
    public void confirmYourFriendRequest(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriend) {
        userService.confirmYourFriendRequest(idUser, idFriend);
    }


}
