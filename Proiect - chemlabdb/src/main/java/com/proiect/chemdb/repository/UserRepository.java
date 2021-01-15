package com.proiect.chemdb.repository;

import com.proiect.chemdb.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findBy(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        RowMapper<User> mapper = getUserRowMapper();

        return jdbcTemplate.query(sql, mapper, id).stream().findAny();
    }

    public User save(User user) {
        String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update( connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setBoolean(4, user.getAdmin());
            preparedStatement.setBoolean(5, user.getRestrictedAccess());

            return preparedStatement;
        }, holder);
        user.setId(holder.getKey().longValue());

        return user;
    }

    public User update(User user) {
        String sql = "UPDATE users SET name = ?, role = ?, restrictedAccess = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getRole(), user.getRestrictedAccess(), user.getId());

        sql = "SELECT * FROM users WHERE id = ?";
        RowMapper<User> mapper = getUserRowMapper();

        return jdbcTemplate.query(sql, mapper, user.getId()).stream().findFirst().get();
    }


    private RowMapper<User> getUserRowMapper() {
        return (resultSet, i) -> new User(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("role"),
                resultSet.getBoolean("admin"),
                resultSet.getBoolean("restrictedAccess"));
    }



}
