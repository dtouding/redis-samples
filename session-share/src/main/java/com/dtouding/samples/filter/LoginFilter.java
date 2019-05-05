package com.dtouding.samples.filter;

import com.dtouding.samples.common.Const;
import com.dtouding.samples.util.CookieUtil;
import com.dtouding.samples.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().startsWith("/login")
            || request.getRequestURI().endsWith(".js")
            || request.getRequestURI().endsWith(".css")
            || request.getRequestURI().endsWith(".html")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String loginToken = Const.RedisKeyPrefix.REDIS_SESION_ID_PREFIX + token;
            if (RedisUtil.exists(loginToken)) {
                RedisUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        response.sendRedirect("/login");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
