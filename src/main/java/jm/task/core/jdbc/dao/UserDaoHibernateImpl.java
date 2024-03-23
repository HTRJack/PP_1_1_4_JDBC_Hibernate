package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
        // default constructor
    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createSQLQuery("""
                CREATE TABLE IF NOT EXISTS USERS (
                `id` BIGINT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL,
                `lastName` VARCHAR(45) NULL,
                `age` TINYINT NULL,
                PRIMARY KEY (`id`));
                """).executeUpdate();

        session.getTransaction().commit();
        Util.closeSessionFactory();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createSQLQuery("DROP TABLE IF EXISTS USERS").executeUpdate();

        session.getTransaction().commit();
        Util.closeSessionFactory();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.save(new User(name, lastName, age));

        session.getTransaction().commit();
        Util.closeSessionFactory();
        System.out.printf("User с именем — %s добавлен в базу данных %n", name);

    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery(String.format("DELETE FROM USERS WHERE id = %s", id)).executeUpdate();

        session.getTransaction().commit();
        Util.closeSessionFactory();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<User> allUsers = session.createQuery("FROM USERS").getResultList();

        session.getTransaction().commit();
        Util.closeSessionFactory();

        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        session.createQuery("DELETE FROM USERS").executeUpdate();

        session.getTransaction().commit();
        Util.closeSessionFactory();
    }
}
