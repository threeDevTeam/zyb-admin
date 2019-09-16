package com.hthyaq.zybadmin.common.interceptor;

import com.google.common.base.Strings;
import com.hthyaq.zybadmin.common.constants.GlobalConstants;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取session域中的user_id
        String userId = (String) request.getSession().getAttribute(GlobalConstants.USER_ID);
        if (Strings.isNullOrEmpty(userId)) {
            throw new RuntimeException(GlobalConstants.USER_NO_LOGIN);
        }
        return true;
    }
}
