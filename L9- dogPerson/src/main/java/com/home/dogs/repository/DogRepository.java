package com.home.dogs.repository;

import com.home.dogs.domain.Dog;
import com.home.dogs.domain.Person;
import com.home.dogs.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Dog> findAll() {
        String sql = "SELECT * FROM dog";
        RowMapper<Dog> mapper = getDogRowMapper();
        return jdbcTemplate.query(sql, mapper);
    }

    public Optional<Dog> findBy(Long id) {
        String sql = "SELECT * FROM dog WHERE id = ?";
        RowMapper<Dog> mapper = getDogRowMapper();
        return jdbcTemplate.query(sql, mapper, id).stream().findAny();
    }

    public void save(Dog dog) {
        /*if(dog.getHasOwner()) {
            String sql = "INSERT INTO person VALUES(NULL, ?, ?, ?)";
            jdbcTemplate.update(sql, dog.getOwner().getName(), dog.getOwner().getAge(), dog.getOwner().getCity());
        };*/

        String sql = "INSERT INTO dog VALUES(NULL, ?, ?, ?, ?, ?, ?, ? ,?)";
        jdbcTemplate.update(sql, dog.getName(), dog.getAge(), dog.getBreed(), dog.getColor(), dog.getHasCode(), dog.getCode(), dog.getHasOwner());
    }

    public void update(Long id, String name) {
        String sql = "UPDATE dog SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM dog WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Dog> getDogRowMapper() {
        return ((resultSet, i) -> new Dog(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("breed"),
                resultSet.getString("color"),
                resultSet.getBoolean("hasCode"),
                resultSet.getString("code"),
                resultSet.getBoolean("hasOwner")
                //resultSet.getPerson("owner")
        ));
    }
}
