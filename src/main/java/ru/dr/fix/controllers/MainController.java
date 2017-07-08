package ru.dr.fix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dr.fix.mappers.UserMapper;
import ru.dr.fix.models.User;

import java.util.List;

/**
 * Created by Dr.Raim on 02-Jul-17.
 */
@RestController
public class MainController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/connect")
    public void connect() {
        //
    }

    @GetMapping("/users")
    public List<User> userList() {
        return userMapper.findAllUsers();
    }

    @PostMapping(value = "/add")
    public void addUser(@ModelAttribute User user) {
        userMapper.addUser(user);
    }

    @GetMapping("/disconnect")
    public void disconnect() {
//
    }

}
