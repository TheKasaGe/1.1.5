package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "CREATE TABLE users (" +
                    "id INT PRIMARY KEY UNIQUE AUTO_INCREMENT, " +
                    "name VARCHAR(20)," +
                    "lastName VARCHAR(20), " +
                    "age INT);";
            statement.execute(SQL);
            System.out.println("Таблица users успешно создана");
        } catch (SQLException e) {
            System.out.println("Таблица users уже создана");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "DROP TABLE users";
            statement.execute(SQL);
            System.out.println("Таблица users успешно удалена");
        } catch (SQLException e) {
            System.out.println("Таблица users уже была удалена");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
//        try (PreparedStatement statement = Util.getConnection().prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?,?,?);")) {
//            statement.setString(1, name);
//            statement.setString(2, lastName);
//            statement.setByte(3, age);
//            System.out.println("User с именем – " + name + " добавлен в базу данных");
//        } catch (SQLException e) {
//            System.out.println("User с именем – " + name + " НЕ добавлен в базу данных!!!");
//            e.printStackTrace();
//        }
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "INSERT INTO users (name, lastName, age) " +
                    "VALUES ('" + name + "', '" + lastName + "', '" + age + "');";
            statement.execute(SQL);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("User с именем – " + name + " НЕ добавлен в базу данных!!!");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "DELETE from users where id = " + id + ";";
            statement.execute(SQL);
            System.out.println("User c id = " + id + " успешно удален");
        } catch (SQLException e) {
            System.out.println("При удалении User'a с id =" + id + " произошла ошибка");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String SQL = "DELETE from users";
            statement.execute(SQL);
            System.out.println("Таблица успешно очищена");
        } catch (SQLException e) {
            System.out.println("При очистки таблицы произошла ошибка");
        }
    }
}
