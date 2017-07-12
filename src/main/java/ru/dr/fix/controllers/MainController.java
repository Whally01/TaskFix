package ru.dr.fix.controllers;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dr.fix.models.User;
import ru.dr.fix.services.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dr.Raim on 02-Jul-17.
 */
@RestController
//@Scope("prototype")
public class MainController {
    Connection connection;
    Liquibase liquibase;

    @Autowired
    UserService userService;

    @GetMapping("/connect")
    public void connect() throws LiquibaseException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/users", "postgres", "postgres");

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        liquibase = new liquibase.Liquibase("liquibase.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());
    }

    @GetMapping("/users")
    public List<User> userList() {
        //   System.out.println(this.hashCode());
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
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
