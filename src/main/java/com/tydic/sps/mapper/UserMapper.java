package com.tydic.sps.mapper;

import com.tydic.sps.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-13
 * Time: 下午12:57
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE USERID = #{id}")
    User findUserById(int id);
    @Select("SELECT * FROM USER")
    List<User> findUserList();
    @Insert("INSERT INTO USER (USERNAME, PASSWORD) VALUES (#{username}, #{password})")
    int insertUser(User user);
}
