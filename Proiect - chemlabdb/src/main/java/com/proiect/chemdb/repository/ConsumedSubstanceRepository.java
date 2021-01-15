package com.proiect.chemdb.repository;

import com.proiect.chemdb.domain.ConsumedSubstance;
import com.proiect.chemdb.domain.Substance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ConsumedSubstanceRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ConsumedSubstanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<ConsumedSubstance>> findAll() {
        String sql = "SELECT * FROM consumed";

        RowMapper<ConsumedSubstance> mapper = getSubstanceRowMapper();

        List<ConsumedSubstance> result =  jdbcTemplate.query(sql, mapper);

        return check(result);
    }

    public Optional<List<ConsumedSubstance>> findBySubstance(Long id) {
        String sql = "SELECT * FROM consumed WHERE substanceId = ?";
        RowMapper<ConsumedSubstance> mapper = getSubstanceRowMapper();

        List<ConsumedSubstance> result = jdbcTemplate.query(sql, mapper, id);

        return check(result);
    }

    public Optional<List<ConsumedSubstance>> findByUser(Long id) {
        String sql = "SELECT * FROM consumed WHERE userId = ?";
        RowMapper<ConsumedSubstance> mapper = getSubstanceRowMapper();

        List<ConsumedSubstance> result = jdbcTemplate.query(sql, mapper, id);

        return check(result);
    }

    public ConsumedSubstance addEntry(ConsumedSubstance substance) {
        String sql = "INSERT INTO consumed VALUES (?, ?, ?, ? )";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update( connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, substance.getSubstanceId());
            preparedStatement.setFloat(3, substance.getConsumedAmount());
            preparedStatement.setLong(4, substance.getUserId());

            return preparedStatement;
        }, holder);
        substance.setId(holder.getKey().longValue());

        return substance;
    }

    private RowMapper<ConsumedSubstance> getSubstanceRowMapper() {
        return (resultSet, i) -> new ConsumedSubstance(
                resultSet.getLong("id"),
                resultSet.getLong("substanceId"),
                resultSet.getFloat("consumedAmount"),
                resultSet.getLong("userId"));
    }

    private Optional<List<ConsumedSubstance>> check(List<ConsumedSubstance> result) {
        if(result!=null && !result.isEmpty()) {
            return Optional.of(result);
        }
        else {
            return Optional.empty();
        }
    }
}
