package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserDaoJDBCImpl implements UserDao {
    Logger log = Logger.getLogger("UserDaoJDBCImpl");
    String query;

    public UserDaoJDBCImpl() {
        // default constructor
    }

    public void createUsersTable() {

        query = """
                CREATE TABLE IF NOT EXISTS USERS (
                `id` BIGINT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL,
                `lastName` VARCHAR(45) NULL,
                `age` TINYINT NULL,
                PRIMARY KEY (`id`));
                """;

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void dropUsersTable() {
        query = "DROP TABLE IF EXISTS USERS;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT USERS (name, lastName, age)" +
                "VALUES (?,?,?);";
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            log.info(String.format("User с именем — %s добавлен в базу данных%n", name));
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        query = "DELETE FROM USERS WHERE id = ?;";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate(query);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        query = "SELECT * FROM USERS;";

        List<User> allUsers = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3), resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        query = "DELETE FROM USERS where id!=0;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
