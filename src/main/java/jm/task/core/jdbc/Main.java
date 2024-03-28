package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.logging.Logger;

public class Main {


    public static void main(String[] args) {
        Logger log = Logger.getLogger("Main");
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.cleanUsersTable();

        userService.saveUser("Биба", "Бобов", (byte) 25);
        userService.saveUser("Боба", "Бибов", (byte) 17);
        userService.saveUser("Вин", "Дроссель", (byte) 19);
        userService.saveUser("Адам", "Арабов", (byte) 61);


        for (User user : userService.getAllUsers()) {
            log.info(user.toString());
        }


        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
