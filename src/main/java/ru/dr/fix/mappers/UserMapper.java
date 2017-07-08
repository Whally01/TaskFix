package ru.dr.fix.mappers;

import org.apache.ibatis.annotations.Select;
import ru.dr.fix.models.User;

import java.util.List;

/**
 * Created by Dr.Raim on 02-Jul-17.
 */
public interface UserMapper {
  //  @Select("select * from users")
    List<User> findAllUsers();

    void addUser(User user);
}
