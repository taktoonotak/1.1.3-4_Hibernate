package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection CONNECTION = Util.getConnection();

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(45), " +
            "lastName VARCHAR(45), " +
            "age TINYINT)";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";

    private static final String SAVE_USER_SQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM users WHERE id = ?";

    private static final String GET_ALL_USERS_SQL = "SELECT * FROM users";

    private static final String CLEAN_USERS_TABLE_SQL = "DELETE FROM users";


    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(DROP_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = CONNECTION.prepareStatement(SAVE_USER_SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement statement = CONNECTION.prepareStatement(REMOVE_USER_BY_ID_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = CONNECTION.createStatement(); ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}