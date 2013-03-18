package com.tydic.sps.controller;

import com.tydic.sps.mapper.UserMapper;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-3-18
 * Time: 下午8:49
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ShowUser {
    @Resource
    private UserMapper userMapper;
    public String findUser(){
        return userMapper.findUserById(1).getUsername();
    }
}
