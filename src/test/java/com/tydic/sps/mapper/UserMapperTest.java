package com.tydic.sps.mapper;

import com.tydic.sps.domain.User;
import com.tydic.sps.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-13
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest {
    @Resource
    UserMapper userMapper;
    @Resource
    UserService userService;

    @Test
    public void testFindUserById() throws Exception {
        System.out.println("hello test");
        User user = userService.findUserById(1);
        System.out.println(user.getUsername());

    }

    @Test
    public void testFindUserList() throws Exception {
        for(User u : userMapper.findUserList()) {
            System.out.println(u.getUsername());
        }
    }

    @Test
    public void testInsertUser() throws Exception {
        User user = new User();
        user.setUsername("xuxiaoy");
        user.setPassword("xuxiaoya");
        int i = userMapper.insertUser(user);
        System.out.println(i);
    }
}
