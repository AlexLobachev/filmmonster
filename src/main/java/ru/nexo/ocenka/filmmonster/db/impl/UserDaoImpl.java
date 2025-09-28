package ru.nexo.ocenka.filmmonster.db.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.db.UserDao;
import ru.nexo.ocenka.filmmonster.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component("userDao")
@Slf4j
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    private String sqlQuery;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;


    }

    @Override
    public User createUser(User user) {
        sqlQuery = ("INSERT INTO users (email, login_user, name_user, birthday) " +
                "VALUES (?,?,?,?)");
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return getLastUser();
    }

    @Override
    public User updateUser(User user) {
        sqlQuery = ("UPDATE users SET " +
                "email = ?, " +
                "login_user = ?, " +
                "name_user = ?, " +
                "birthday = ? " +
                "WHERE id_user = ?");
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public List<User> getAllUser() {
        String sqlQuery = ("SELECT * " +
                "FROM users");
        return jdbcTemplate.query(sqlQuery, this::userMapper);
    }


    @Override
    public User getUser(int idUser) {

        sqlQuery = ("" +
                "SELECT * " +
                "FROM users " +
                "WHERE id_user = ? ");

        return jdbcTemplate.queryForObject(sqlQuery, this::userMapper, idUser);

    }

    @Override
    public Map<String, Integer> addFriend(int idUser, int idFriend) {

        if (getSubscriber(idUser).stream().anyMatch(x -> x.getId().equals(idFriend)))
            return Map.of("Вы уже подписаны на пользователя с ID = ", idFriend);

        sqlQuery = ("INSERT INTO friend_user (id_user, id_friend, status_friend) " +
                "VALUES (?, ?, ?) ");
        jdbcTemplate.update(sqlQuery, idUser, idFriend, 1);
        jdbcTemplate.update(sqlQuery, idFriend, idUser, null);
        return Map.of("Вы подписаны на пользователя с ID = ", idFriend);
    }

    //TODO - Метод не удаляет из друзей а переводит в подписчики
    @Override
    public void deleteFriend(int idUser, int idFriend) {
        sqlQuery = ("UPDATE friend_user " +
                "SET status_friend = ? " +
                "WHERE id_user = ? AND id_friend = ?");
        jdbcTemplate.update(sqlQuery, null, idUser, idFriend);

    }

    @Override
    public List<User> getAllFriend(int idUser) {
        sqlQuery = ("SELECT * " +
                "FROM users AS u " +
                "JOIN friend_user AS fu ON u.id_user = fu.id_friend " +
                "WHERE fu.id_user = ? AND fu.status_friend = ?");


        return jdbcTemplate.query(sqlQuery, this::userMapper, idUser, 1);
    }

    @Override
    public List<User> getCommonFriends(int idUser, int idFriend) {
        sqlQuery = ("SELECT * " +
                "FROM users " +
                "WHERE id_user IN ( " +
                "SELECT  id_friend " +
                "FROM friend_user " +
                "WHERE (id_user = ? OR id_user = ?) AND status_friend = ? " +
                "GROUP BY id_friend " +
                "HAVING COUNT(*) > 1)");
        return jdbcTemplate.query(sqlQuery, this::userMapper, idUser, idFriend, 1);
    }

    @Override
    public void confirmYourFriendRequest(int idUser, int idFriend) {

        sqlQuery = ("UPDATE friend_user " +
                "SET status_friend = ? " +
                "WHERE id_user = ? AND id_friend = ?");
        jdbcTemplate.update(sqlQuery, 1, idUser, idFriend);
        jdbcTemplate.update(sqlQuery, 1, idFriend, idUser);
    }

    @Override
    public void deleteUser(int idUser) {
        sqlQuery = ("DELETE FROM users WHERE id_user = ?");
        jdbcTemplate.update(sqlQuery, idUser);
    }


    private User userMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id_user")).
                name(resultSet.getString("name_user")).
                email(resultSet.getString("email")).
                login(resultSet.getString("login_user")).
                birthday(resultSet.getDate("birthday").toLocalDate()).build();
    }

    private List<User> getSubscriber(int idUser) {
        sqlQuery = ("SELECT * " +
                "FROM users " +
                "WHERE id_user IN ( " +
                "SELECT id_friend " +
                "FROM friend_user " +
                "WHERE id_user = ? AND status_friend = ?)");
        return jdbcTemplate.query(sqlQuery, this::userMapper, idUser, 1);
    }

    private User getLastUser() {
        sqlQuery = ("SELECT * FROM users ORDER BY id_user DESC LIMIT 1");
        return jdbcTemplate.queryForObject(sqlQuery, this::userMapper);
    }


}


