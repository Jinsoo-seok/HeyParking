package com.cudo.heyparking.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
class ErrorPageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/") || (requestURI.startsWith("/page") && !requestURI.equals("/page/main"))) {
            response.sendRedirect("/page/main");
            return false;
        }
        return true;
    }
}