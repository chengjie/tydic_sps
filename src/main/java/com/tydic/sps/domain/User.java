package com.tydic.sps.domain;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: chengjie
 * Date: 13-1-12
 * Time: 下午9:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class User {
    @Id
    private int userid;
    private String username;
    private String password;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
