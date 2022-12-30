package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            String SQL = "CREATE TABLE users (" +
                    "id INT PRIMARY KEY UNIQUE AUTO_INCREMENT, " +
                    "name VARCHAR(20) NOT NULL ," +
                    "lastName VARCHAR(20) NOT NULL , " +
                    "age INT NOT NULL);";

            session.createSQLQuery(SQL)
                    .addEntity(User.class)
                    .executeUpdate();

            transaction.commit();
            System.out.println("Таблица users успешно создана");
        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
            System.err.println("Ошибка при создании таблицы!");

        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            String SQL = "DROP TABLE users;";

            session.createSQLQuery(SQL)
                    .addEntity(User.class)
                    .executeUpdate();

            transaction.commit();
            System.out.println("Таблица users успешно удалена");
        } catch (Exception e) {
//            if (transaction != null) {
//               transaction.rollback();
//            }
            System.err.println("Таблица не существует!");

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при добавлении User'a с именем - " + name);

        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("User с ID – " + id + " успешно удален");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Ошибка при удалении User'a с ID - " + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = new ArrayList<>();
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            list = session.createQuery("FROM User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("При попытке получения User'ов из таблицы произошла ошибка");
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            transaction.commit();
            System.out.println("Таблица успешно очищена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("При очистке таблицы произошла ошибка");
        }
    }
}
