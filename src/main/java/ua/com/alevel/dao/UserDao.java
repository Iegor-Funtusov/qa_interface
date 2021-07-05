package ua.com.alevel.dao;

import ua.com.alevel.User;

import java.util.List;

public interface UserDao {

    void create(User user);
    void update(User user);
    void delete(String id);
    User findById(String id);
    List<User> findAll();
}
