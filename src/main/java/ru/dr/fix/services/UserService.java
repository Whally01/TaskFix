package ru.dr.fix.services;

import ru.dr.fix.models.User;

import java.util.List;

/**
 * Created by Dr.Raim on 10-Jul-17.
 */
public interface UserService {

    List<User> findAllUsers();

    void addUser(User user);

}
