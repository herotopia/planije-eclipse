package web.dao.repository;

import web.beans.User;

import java.util.Collection;

public interface UserRepository {

    void addUser(User user);
    void updateUser(int userId, String username, String firstName, String lastName, String email, String phone);
    void deleteUser(User user);

    User getUserById(int userId);
    User getUserByUsername(String username);
    Collection<User> getUsers();

}
