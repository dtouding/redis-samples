package com.dtouding.samples.controller;

import com.dtouding.samples.common.Const;
import com.dtouding.samples.common.RsResponse;
import com.dtouding.samples.po.User;
import com.dtouding.samples.service.IUserService;
import com.dtouding.samples.util.CookieUtil;
import com.dtouding.samples.util.JsonUtil;
import com.dtouding.samples.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public String toLogin() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public RsResponse<User> login(String username, String password, HttpSession session,
                                  HttpServletResponse response) {
        RsResponse<User> rs = userService.login(username, password);
        if (rs.isSuccess()) {
            RedisUtil.setex(Const.RedisKeyPrefix.REDIS_SESION_ID_PREFIX + session.getId(),
                    JsonUtil.bean2Json(rs.getData()),
                    Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            CookieUtil.writeLoginToken(response, session.getId());
        }
        return rs;
    }
}
