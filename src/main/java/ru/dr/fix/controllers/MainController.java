package ru.dr.fix.controllers;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dr.fix.mappers.UserMapper;
import ru.dr.fix.models.Conn;
import ru.dr.fix.models.User;
import ru.dr.fix.services.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by Dr.Raim on 02-Jul-17.
 */
@RestController
public class MainController {
    Connection connection;
    Liquibase liquibase;
    Configuration configuration;

    @Autowired
    UserService userService;



    @PostMapping("/connect")
    public void connect(@ModelAttribute Conn conn) throws LiquibaseException, SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(conn.getUrl(), conn.getUsername(), conn.getPassword());

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        liquibase = new Liquibase("liquibase.xml", new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());

     /**Adding mapper to configuration*/
        DataSource dataSource = new org.apache.ibatis.datasource.pooled.PooledDataSource(
                "org.postgresql.Driver", conn.getUrl(), conn.getUsername(), conn.getPassword());
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        configuration = new Configuration(environment);
        TypeAliasRegistry aliases = configuration.getTypeAliasRegistry();
        aliases.registerAlias("user", User.class);
        configuration.addMapper(UserMapper.class);
    }


    @GetMapping("/users")
    public List<User> userList() {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        List<User> users = userMapper.findAllUsers();
        session.close();
        return users; //userService.findAllUsers();
    }

    @PostMapping(value = "/add")
    public void addUser(@ModelAttribute User user) {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);

        userMapper.addUser(user);
        session.commit();
        session.close();
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
