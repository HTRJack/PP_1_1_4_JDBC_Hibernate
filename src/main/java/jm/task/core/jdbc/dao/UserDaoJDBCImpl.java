package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    //Statement statement;
    String SQLQuerry;

    public UserDaoJDBCImpl() {
        // default constructor
    }

    public void createUsersTable() {

        SQLQuerry = """
                CREATE TABLE IF NOT EXISTS USERS (
                `id` BIGINT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL,
                `lastName` VARCHAR(45) NULL,
                `age` TINYINT NULL,
                PRIMARY KEY (`id`));
                """;

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLQuerry);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void dropUsersTable() {
        SQLQuerry = "DROP TABLE IF EXISTS KATA.USERS;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLQuerry);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        SQLQuerry = "INSERT KATA.USERS (name, lastName, age)" +
                "VALUES ('" + name + "','" + lastName + "'," + age + ");";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLQuerry);
            System.out.printf("User с именем — %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void removeUserById(long id) {
        SQLQuerry = "DELETE FROM KATA.USERS WHERE id = " + id + ";";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLQuerry);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<User> getAllUsers() {
        SQLQuerry = "SELECT * FROM KATA.USERS;";

        List<User> allUsers = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLQuerry);
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3), resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        SQLQuerry = "DELETE FROM KATA.USERS where id!=0;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQLQuerry);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
