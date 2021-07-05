package ua.com.alevel.dao;

import ua.com.alevel.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Deprecated
public class UserInMemoryDao implements UserDao {

    private List<User> users = new ArrayList<>();

    public UserInMemoryDao() {
        System.out.println("UserInMemoryDao.UserInMemoryDao");
    }

    @Override
    public void create(User user) {
        user.setId(generateId());
        users.add(user);
    }

    @Override
    public void update(User user) {
        User current = users.stream().filter(u -> u.getId().equals(user.getId())).findFirst().get();
        current.setName(user.getName());
        current.setAge(user.getAge());
    }

    @Override
    public void delete(String id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    @Override
    public User findById(String id) {
        return  users.stream().filter(u -> u.getId().equals(id)).findFirst().get();
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (users.stream().anyMatch(user -> user.getId().equals(id))) {
            return generateId();
        }
        return id;
    }
}
