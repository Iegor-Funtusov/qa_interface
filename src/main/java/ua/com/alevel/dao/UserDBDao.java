package ua.com.alevel.dao;

import ua.com.alevel.User;

import java.util.List;

public class UserDBDao implements UserDao {

    public UserDBDao() {
        System.out.println("UserDBDao.UserDBDao");
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
