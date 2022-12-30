package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService q = new UserServiceImpl();
//         1. Создание таблицы
        q.createUsersTable();
//         2. Создание и добавление в таблицу User'ов
        User s1 = new User("Batman", "Ivanov", (byte) 48);
        User s2 = new User("Bond", "Compact", (byte) 22);
        User s3 = new User("Ded", "Outside", (byte) 13);
        User s4 = new User("Lexa", "Lepexa", (byte) 27);

        q.saveUser(s1.getName(), s1.getLastName(), s1.getAge());
        q.saveUser(s2.getName(), s2.getLastName(), s2.getAge());
        q.saveUser(s3.getName(), s3.getLastName(), s3.getAge());
        q.saveUser(s4.getName(), s4.getLastName(), s4.getAge());
//         3. Вывод в консоль всех User'ов
        System.out.println(q.getAllUsers());
//         4. Очистка таблицы
        q.cleanUsersTable();
//         5. Удаление таблицы
        q.dropUsersTable();
    }
}
