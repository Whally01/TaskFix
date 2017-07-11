package ru.dr.fix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dr.fix.mappers.UserMapper;
import ru.dr.fix.models.User;
import ru.dr.fix.services.UserService;

import java.util.List;

/**
 * Created by Dr.Raim on 02-Jul-17.
 */
@RestController
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/connect")
    public void connect() {
        //
    }

    @GetMapping("/users")
    public List<User> userList() {
        return userService.findAllUsers();
    }

    @PostMapping(value = "/add")
    public void addUser(@ModelAttribute User user) {
        userService.addUser(user);
    }

    @Transactional
    @PostMapping(value = "/adduserstx")
    public void addSeveralUsersTx(@ModelAttribute User a, @ModelAttribute User b, @ModelAttribute User c) {
        userService.addUser(a);
        userService.addUser(b);
        userService.addUser(c);
    }

    @PostMapping(value = "/addusers")
    public void addSeveralUsers(@ModelAttribute User a, @ModelAttribute User b, @ModelAttribute User c) {
        userService.addUser(a);
        userService.addUser(b);
        userService.addUser(c);
    }


    @GetMapping("/disconnect")
    public void disconnect() {
//
    }

}
