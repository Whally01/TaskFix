package ru.dr.fix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dr.fix.mappers.UserMapper;
import ru.dr.fix.models.User;

import java.util.List;

/**
 * Created by Dr.Raim on 10-Jul-17.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }
}
