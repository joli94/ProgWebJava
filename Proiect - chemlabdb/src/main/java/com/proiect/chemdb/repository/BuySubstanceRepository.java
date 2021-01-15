package com.proiect.chemdb.repository;

import com.proiect.chemdb.domain.BuySubstance;
import com.proiect.chemdb.domain.Substance;
import com.proiect.chemdb.dto.BuySubstanceDto;
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
public class BuySubstanceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BuySubstanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BuySubstance save(BuySubstance substance) {
        String sql = "INSERT INTO purchases VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update( connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, substance.getCas());
            preparedStatement.setFloat(3, substance.getPurity());
            preparedStatement.setString(4, substance.getSupplier());
            preparedStatement.setString(5, substance.getPacking());
            preparedStatement.setLong(6, substance.getNumber());
            preparedStatement.setLong(7, substance.getUserId());
            preparedStatement.setFloat(8, substance.getPrice());
            preparedStatement.setString(9, substance.getLink());

            return preparedStatement;
        }, holder);
        substance.setId(holder.getKey().longValue());

        return substance;
    }

    public Optional<List<BuySubstance>> getAll() {
        String sql = "SELECT * FROM purchases";
        RowMapper <BuySubstance> mapper = getSubstanceRowMapper();

        List<BuySubstance> result = jdbcTemplate.query(sql, mapper);

        if(result!=null && !result.isEmpty()) {
            return Optional.of(result);
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<List<BuySubstance>> getById(Long id) {
        String sql = "SELECT * FROM purchases WHERE userID = ?";
        RowMapper<BuySubstance> mapper = getSubstanceRowMapper();

        List<BuySubstance> result = jdbcTemplate.query(sql, mapper, id);

        if(result!=null && !result.isEmpty()) {
            return Optional.of(result);
        }
        else {
            return Optional.empty();
        }
    }

    private RowMapper<BuySubstance> getSubstanceRowMapper() {
        return (resultSet, i) -> new BuySubstance(
                resultSet.getLong("id"),
                resultSet.getString("cas"),
                resultSet.getFloat("purity"),
                resultSet.getString("supplier"),
                resultSet.getString("packing"),
                resultSet.getLong("number"),
                resultSet.getLong("userId"),
                resultSet.getFloat("price"),
                resultSet.getString("link"));
    }


}
