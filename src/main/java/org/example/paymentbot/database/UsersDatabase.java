package org.example.paymentbot.database;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersDatabase implements Dao<User>{

    private final JdbcConnectionPool connectionPool;

    public UsersDatabase(JdbcConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<User> get(long id) {
        String sql = "SELECT * FROM Users u WHERE u.id=?";
        try (var con = connectionPool.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                User u = new User(resultSet.getLong(1),
                        resultSet.getInt(2), resultSet.getString(3));
                return Optional.of(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try(var con = connectionPool.getConnection()) {
            try(var statement = con.prepareStatement("SELECT * FROM Users")) {
                try(var resultSet = statement.executeQuery()) {
                    List<User> users = new ArrayList<>();
                    while (resultSet.next()) {
                        User u = new User(resultSet.getLong(0),
                                resultSet.getInt(1), resultSet.getString(2));
                        users.add(u);
                    }
                    return users;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        try(var con = connectionPool.getConnection()) {
            String sql = "INSERT INTO Users (id, domainId, name) VALUES (?, ?, ?)";
            try (var statement = con.prepareStatement(sql)) {
                statement.setLong(1, user.getId());
                statement.setInt(2, user.getDomain());
                statement.setString(3, user.getName());
                statement.execute();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            update(user,null);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user, String[] params) {
        try(var con = connectionPool.getConnection()){
            String sql = "UPDATE Users SET DOMAINID=?,name=? WHERE id=?";
            try(PreparedStatement statement = con.prepareStatement(sql)){
                statement.setInt(1, user.getDomain());
                statement.setLong(3, user.getId());
                statement.setString(2, user.getName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete user from dababase Users
    @Override
    public void delete(User user) {
        try(var con = connectionPool.getConnection()) {
            try (var statement = con.prepareStatement("DELETE FROM Users WHERE id=?")) {
                statement.setLong(1, user.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void create() {
        try(var con = connectionPool.getConnection()) {
            try(var statement = con.prepareStatement("CREATE TABLE IF NOT EXISTS Users " +
                    "(id BIGINT PRIMARY KEY, domainId BIGINT, name VARCHAR(255)," +
                    "foreign key (domainId) references Domains(id))")) {
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
