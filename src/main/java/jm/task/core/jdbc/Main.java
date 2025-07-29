package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan","Ivanov",(byte)34);
        userService.saveUser("Igor","Petrov",(byte)44);
        userService.saveUser("Vladimir","Sidor",(byte)55);
        userService.saveUser("Alex","Smirnov",(byte)65);

        userService.removeUserById(1);

        List<User>users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
