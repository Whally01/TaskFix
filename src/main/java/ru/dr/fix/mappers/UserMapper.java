package ru.dr.fix.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Scope;
import ru.dr.fix.models.User;

import java.util.List;

/**
 * Created by Dr.Raim on 02-Jul-17.
 */
public interface UserMapper {
    @Select("select * from users")
    List<User> findAllUsers();

    @Insert("insert into users(name, login, password) values (#{name}, #{login}, #{password})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    void addUser(User user);
}
