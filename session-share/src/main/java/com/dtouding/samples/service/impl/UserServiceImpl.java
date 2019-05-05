package com.dtouding.samples.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.dtouding.samples.common.RsResponse;
import com.dtouding.samples.dao.UserDao;
import com.dtouding.samples.po.User;
import com.dtouding.samples.service.IUserService;
import com.dtouding.samples.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserDao userDao;

    @Override
    public RsResponse<User> login(String username, String password) {
        User user = userDao.getUser(username);
        if (user == null) {
            return RsResponse.error("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        System.out.println(md5Password);
        if (!StringUtils.equals(md5Password, user.getPassword())) {
            return RsResponse.error("密码错误");
        }
        user.setPassword("");
        return RsResponse.success("登录成功", user);
    }
}
