package com.home.dogs.repository;

import com.home.dogs.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Person> findAll() {
        String sql = "SELECT * FROM person";
        RowMapper<Person> mapper = getPersonRowMapper();
        return jdbcTemplate.query(sql, mapper);
    }

    public Optional<Person> findBy(Long id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        RowMapper<Person> mapper = getPersonRowMapper();
        return jdbcTemplate.query(sql, mapper, id).stream().findAny();
    }

    public void save(Person person) {
        String sql = "INSERT INTO person VALUES(NULL, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getAge(), person.getCity());
    }

    public void update(Long id, String name) {
        String sql = "UPDATE person SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Person> getPersonRowMapper() {
        return ((resultSet, i) -> new Person(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("city")
        ));
    }
}
