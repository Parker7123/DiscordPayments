package org.example.paymentbot.database;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DomainsDadabase implements Dao<Domain> {

    private final JdbcConnectionPool connectionPool;

    public DomainsDadabase(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Domain> get(long id) {
        String sql = "SELECT * FROM Domains d WHERE d.id=?";
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                Domain d = new Domain(resultSet.getNString(0),
                        resultSet.getInt(1));
                return Optional.of(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Domain> get(String apiUrl) {
        String sql = "SELECT * FROM Domains d WHERE d.apiUrl=?";
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, apiUrl);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Domain> domains = new ArrayList<>();
                while (resultSet.next()) {
                    Domain d = new Domain(resultSet.getNString(2),
                            resultSet.getInt(1));
                    domains.add(d);
                }
                return domains;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Domain> getAll() {
        try (var con = connectionPool.getConnection()) {
            try (var statement = con.prepareStatement("SELECT * FROM Domains")) {
                try (var resultSet = statement.executeQuery()) {
                    var domains = new ArrayList<Domain>(resultSet.getFetchSize());
                    while (resultSet.next()) {
                        domains.add(new Domain(resultSet.getNString(1),
                                resultSet.getInt(2)));
                    }
                    return domains;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Domain domain) {
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement("INSERT INTO Domains (apiUrl) VALUES (?)")) {
            statement.setString(1, domain.getApiUrl());
            statement.execute();
        } catch (SQLIntegrityConstraintViolationException ignored) {}
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Domain domain, String[] params) {
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement("UPDATE Domains SET apiUrl=? WHERE id=?")) {
            statement.setString(1, domain.getApiUrl());
            statement.setLong(2, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Domain domain) {
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement("DELETE FROM Domains WHERE id=?")) {
            statement.setLong(1, domain.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create() {
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS Domains (id BIGINT AUTO_INCREMENT, apiUrl VARCHAR UNIQUE , PRIMARY KEY (id))")) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
