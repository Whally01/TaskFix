package ru.dr.fix;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.dr.fix.mappers.UserMapper;
import ru.dr.fix.models.User;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dr.Raim on 04-Jul-17.
 */
public class MAin {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory;
        UserMapper userMapper;
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            userMapper = sqlSessionFactory.openSession().getMapper(UserMapper.class);
         //   List<User> users = userMapper.findAllUsers();
           // users.forEach(user -> System.out.println(user.getLogin()));
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
