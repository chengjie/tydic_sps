package com.tydic.sps.service;

import com.tydic.sps.domain.User;
import com.tydic.sps.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-3-7
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User findUserById(int id){
        return userMapper.findUserById(id);
    }
}
