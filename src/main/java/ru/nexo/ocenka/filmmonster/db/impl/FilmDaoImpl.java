package ru.nexo.ocenka.filmmonster.db.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nexo.ocenka.filmmonster.db.FilmDao;
import ru.nexo.ocenka.filmmonster.db.GenreDao;
import ru.nexo.ocenka.filmmonster.db.RatingDao;
import ru.nexo.ocenka.filmmonster.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Component("filmDao")
@Slf4j
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;
    @Qualifier("genreDao")
    private final GenreDao genreDao;
    @Qualifier("ratingDao")
    private final RatingDao ratingDao;
    private String sqlQuery;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate, GenreDao genreDao, RatingDao ratingDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
        this.ratingDao = ratingDao;
    }

    @Override
    public Film createFilm(Film film) {

        sqlQuery = ("INSERT INTO film (name_film, description, release_date, duration, rate) VALUES (?,?,?,?,?)");
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate());

        film = filmBuilder(film, getIdFilm());

        ratingDao.addRatingFilmById(film);
        genreDao.addGenreFilmById(film);

        return getFilm(film.getId());
    }

    @Override
    public Film updateFilm(Film film) {

        sqlQuery = ("UPDATE film SET name_film = ?, description = ?, release_date = ?, duration = ?, rate = ? WHERE id_film = ?");
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRate(), film.getId());
        ratingDao.updateRatingFilmById(film);
        genreDao.updateGenreFilmById(film);

        return getFilm(film.getId());
    }

    @Override
    public Film getFilm(int id) {
        sqlQuery = ("SELECT * FROM film WHERE id_film = ?");
        return jdbcTemplate.queryForObject(sqlQuery, this::filmMapper, id);
    }

    @Override
    public List<Film> getAllFilm() {
        sqlQuery = "SELECT * FROM film";
        return jdbcTemplate.query(sqlQuery, this::filmMapper);
    }


    @Override
    public void deleteFilm(int idFilm) {
        sqlQuery = ("DELETE FROM film WHERE id_film = ?");
        jdbcTemplate.update(sqlQuery, idFilm);
    }

    @Override
    public void addLikeFilm(int idFilm, int idUser) {
        sqlQuery = ("UPDATE film SET rate = rate + 1 WHERE id_film = ?");
        jdbcTemplate.update(sqlQuery, idFilm);
        sqlQuery = ("INSERT INTO film_like (id_film, id_user) VALUES (?,?)");
        jdbcTemplate.update(sqlQuery, idFilm, idUser);

    }

    @Override
    public void deleteLikeFilm(int idFilm, int idUser) {
        sqlQuery = ("UPDATE film SET rate = rate - 1 WHERE id_film = ?");
        jdbcTemplate.update(sqlQuery, idFilm);
        sqlQuery = ("DELETE FROM film_like WHERE id_user = ?");
        jdbcTemplate.update(sqlQuery, idUser);
    }

    //ЗАКОНЧИЛ ТУТА
    @Override
    public List<Film> getPopularFilmTenLike(int count) {
        sqlQuery = ("" +
                "SELECT * " +
                "FROM film " +
                "ORDER BY rate DESC " +
                "LIMIT ?");
        return jdbcTemplate.query(sqlQuery, this::filmMapper, count);
    }

    private Film filmMapper(ResultSet resultSet, int rowNum) throws SQLException {
        int idFilm = resultSet.getInt("id_film");
        Film film = Film.builder()
                .id(idFilm)
                .name(resultSet.getString("name_film"))
                .description(resultSet.getString("description"))
                .duration(resultSet.getInt("duration"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .rate(resultSet.getInt("rate"))
                .genres(new HashSet<>(genreDao.getAllGenreByIdFilm(idFilm)))
                .mpa(ratingDao.getRatingBiIdFilm(idFilm))
                .build();
        if (film.getGenres().isEmpty())
            return Film.builder()
                    .id(idFilm)
                    .name(resultSet.getString("name_film"))
                    .description(resultSet.getString("description"))
                    .duration(resultSet.getInt("duration"))
                    .releaseDate(resultSet.getDate("release_date").toLocalDate())
                    .rate(resultSet.getInt("rate"))
                    .mpa(ratingDao.getRatingBiIdFilm(idFilm))
                    .build();
        return film;
    }

    private Integer getIdFilm() {
        sqlQuery = ("SELECT * " +
                "FROM film " +
                "ORDER BY id_film DESC " +
                "LIMIT 1");
        return jdbcTemplate.queryForObject(sqlQuery, this::filmMapper).getId();
    }

    private Film filmBuilder(Film film, int idFilm) {

        return Film.builder()
                .id(idFilm)
                .name(film.getName())
                .description(film.getDescription())
                .duration(film.getDuration())
                .releaseDate(film.getReleaseDate())
                .genres(film.getGenres())
                .mpa(film.getMpa())
                .build();
    }


}



