package com.proiect.chemdb.repository;

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
public class SubstanceRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SubstanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Substance> findById(Long id) {
        String sql = "SELECT * FROM substances WHERE id = ?";
        RowMapper<Substance> mapper = getSubstanceRowMapper();

        return jdbcTemplate.query(sql, mapper, id).stream().findAny();
    }

    public Substance save(Substance substance) {
        String sql = "INSERT INTO substances VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update( connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, substance.getCas());
            preparedStatement.setString(3, substance.getName());
            preparedStatement.setString(4, substance.getMolecularFormula());
            preparedStatement.setFloat(5, substance.getPurity());
            preparedStatement.setString(6, substance.getSupplier());
            preparedStatement.setString(7, substance.getPacking());
            preparedStatement.setFloat(8, substance.getAvailableQuantity());
            preparedStatement.setInt(9,substance.getOwnerId());
            preparedStatement.setBoolean(10, substance.getRestricted());
            preparedStatement.setFloat(11, substance.getPrice());
            preparedStatement.setString(12, substance.getLink());

            return preparedStatement;
        }, holder);
        substance.setId(holder.getKey().longValue());

        return substance;
    }

    public Optional<List<Substance>> findByCas(String query) {
        String sql = "SELECT * FROM substances WHERE cas = ?";

        return findBySqlQuery(sql, query);
    }

    public Optional<List<Substance>> findByMolecularFormula(String query) {
        String sql = "SELECT * from substances WHERE molecularFormula = ?";

        return findBySqlQuery(sql, query);
    }

    public Optional<List<Substance>> findByName(String query) {
        String sql = "SELECT * FROM substances WHERE name = ?";

        return findBySqlQuery(sql, query);
    }

    public Optional<List<Substance>> findByUser(Long id) {
        String sql = "SELECT * FROM substances WHERE ownerId = ?";
        RowMapper<Substance> mapper = getSubstanceRowMapper();

        List<Substance> result =  jdbcTemplate.query(sql, mapper, id);

        if(result!=null && !result.isEmpty()) {
            return Optional.of(result);
        }
        else {
            return Optional.empty();
        }
    }

    public void updateAvailableQuantity(float availableQuantity, Long id) {
        String sql = "UPDATE substances SET availableQuantity = ? WHERE id = ?";

        jdbcTemplate.update(sql, availableQuantity, id);
    }

    private RowMapper<Substance> getSubstanceRowMapper() {
        return (resultSet, i) -> new Substance(
           resultSet.getLong("id"),
           resultSet.getString("cas"),
           resultSet.getString("name"),
           resultSet.getString("molecularFormula"),
           resultSet.getFloat("purity"),
           resultSet.getString("supplier"),
           resultSet.getString("packing"),
           resultSet.getFloat("availableQuantity"),
           resultSet.getInt("ownerId"),
           resultSet.getBoolean("restricted"),
            resultSet.getFloat("price"),
            resultSet.getString("link"));
    }

    private Optional<List<Substance>> findBySqlQuery(String sql, String query) {
        RowMapper<Substance> mapper = getSubstanceRowMapper();

        List<Substance> result =  jdbcTemplate.query(sql, mapper, query);

        if(result!=null && !result.isEmpty()) {
            return Optional.of(result);
        }
        else {
            return Optional.empty();
        }
    }
}
