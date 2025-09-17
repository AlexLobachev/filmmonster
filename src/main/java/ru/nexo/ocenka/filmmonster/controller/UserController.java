package ru.nexo.ocenka.filmmonster.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nexo.ocenka.filmmonster.exception.NotFound;
import ru.nexo.ocenka.filmmonster.model.User;
import ru.nexo.ocenka.filmmonster.service.UserService;
import ru.nexo.ocenka.filmmonster.storage.UserStorage;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final UserStorage userStorage;
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

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriend) {
        if (idUser==idFriend){
            throw new ValidationException("Попытка добавить в друзья самого себя");
        }
        userStorage.getUser(idFriend);
        return userService.addFriend(idUser, idFriend);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriend) {
        userStorage.getUser(idFriend);
        return userService.deleteFriend(idUser, idFriend);
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


}
