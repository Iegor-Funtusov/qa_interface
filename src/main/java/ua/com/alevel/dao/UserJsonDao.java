package ua.com.alevel.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import ua.com.alevel.User;
import ua.com.alevel.config.FileType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Deprecated
public class UserJsonDao implements UserDao {

    List<User> userList = Collections.emptyList();

    public UserJsonDao() {
        System.out.println("UserJsonDao.UserJsonDao");
    }

    @Override
    public void create(User user) {
        this.userList = getUsersFromFile();
        user.setId(generateId());
        this.userList.add(user);
        writeUsersToFile();
    }

    @Override
    public void update(User user) {
        userList = getUsersFromFile();
        if (CollectionUtils.isEmpty(userList)) {
            throw new RuntimeException("empty list");
        }
        User current = userList.stream().filter(u -> u.getId().equals(user.getId())).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            throw new RuntimeException("user not found");
        }
        current.setName(user.getName());
        current.setAge(user.getAge());
        writeUsersToFile();
    }

    @Override
    public void delete(String id) {
        userList = getUsersFromFile();
        if (CollectionUtils.isEmpty(userList)) {
            throw new RuntimeException("empty list");
        }
        User current = userList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            throw new RuntimeException("user not found");
        }
        userList.removeIf(u -> u.getId().equals(id));
        writeUsersToFile();
    }

    @Override
    public User findById(String id) {
        userList = getUsersFromFile();
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        User current = userList.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            return null;
        }
        return current;
    }

    @Override
    public List<User> findAll() {
        return getUsersFromFile();
    }

    private List<User> getUsersFromFile() {
        try {
            String usersJson = FileUtils.readFileToString(new File(FileType.JSON_TYPE.getPath()), "UTF-8");
            if (StringUtils.isBlank(usersJson)) {
                return new ArrayList<>();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(usersJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("some problem from file");
    }

    private void writeUsersToFile() {
        Gson gson = new Gson();
        String usersOut = gson.toJson(this.userList);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileType.JSON_TYPE.getPath()))) {
            writer.append(usersOut);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (userList.stream().anyMatch(u -> u.getId().equals(id))) {
            return generateId();
        }
        return id;
    }
}
