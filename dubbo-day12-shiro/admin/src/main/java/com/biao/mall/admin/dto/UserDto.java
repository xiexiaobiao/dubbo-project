package com.biao.mall.admin.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Classname UserDto
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 19:13
 * @Version 1.0
 **/
@Data
@ToString
public class UserDto implements Serializable {
    private static final long serialVersionID = -9077975168976887742L;
    private String username;
    private char[] password;
    private String encryptPwd;
    private Long userId;
    private String salt;
    private List<String> roles;

    public static long getSerialVersionID() {
        return serialVersionID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getEncryptPwd() {
        return encryptPwd;
    }

    public void setEncryptPwd(String encryptPwd) {
        this.encryptPwd = encryptPwd;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                ", encryptPwd='" + encryptPwd + '\'' +
                ", userId=" + userId +
                ", salt='" + salt + '\'' +
                ", roles=" + roles +
                '}';
    }
}
