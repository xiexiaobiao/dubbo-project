package com.biao.mall.admin.service;

import com.biao.mall.common.dto.UserDto;
import com.biao.mall.admin.util.JwtUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname UserService
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-09-05 20:01
 * @Version 1.0
 **/
@Service
public class UserService {

    private final static Logger lgger = LoggerFactory.getLogger(UserService.class);
    //加密用户信息的盐
    private static final String encryptSalt = "510fdb7f28534fb584af25697826c203";
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public UserService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String generateJwtToken(String username){
        //加密JWT的盐
        String salt = "0805c99fd2634c80b2cde8c7e4124468";
        //redis缓存salt
        stringRedisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
        return JwtUtils.sign(username,salt,60*60);//生成jwt token，设置过期时间为1小时
    }

    /*
     * 获取上次token生成时的salt值和登录用户信息*/
    public UserDto getJwtToken(String username) {
//        String salt = "9723612f53";
        //从数据库或者缓存中取出jwt token生成时用的salt
        String salt = stringRedisTemplate.opsForValue().get("token:"+username);
        UserDto userDto = this.getUserInfo(username);
        userDto.setSalt(salt);
        return userDto;
    }

    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码.这里省去了DB操作，直接生成了用户信息
     * @param username
     * @return
     */
    public UserDto getUserInfo(String username){
        UserDto user =  new UserDto();
        user.setUserId(1L);
        user.setUsername("admin");
        //模拟对密码加密
        user.setEncryptPwd(new Sha256Hash("admin123",encryptSalt).toHex());
        lgger.debug("UserService: [{}]",user.toString());
        return user;
    }

    /**清除token信息*/
    public void deleteLogInfo(String username){
         // 删除数据库或者缓存中保存的salt
//        stringRedisTemplate.delete("token:"+username);
    }

    /**获取用户角色列表，强烈建议从缓存中获取*/
    public List<String> getUserRoles(Long userId){
        //模拟admin角色
        return Arrays.asList("admin");
    }
}
