package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.cleanUsersTable();

        userService.saveUser("Биба", "Бобов", (byte) 25);
        userService.saveUser("Боба", "Бибов", (byte) 17);
        userService.saveUser("Вин", "Дроссель", (byte) 19);
        userService.saveUser("Адам", "Арабов", (byte) 61);


        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

        //userDao.cleanUsersTable();
        userService.dropUsersTable();
    }
}
