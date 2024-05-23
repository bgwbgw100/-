package com.example.demo.interceptor;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;

@Component
public class BoardInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String boardKind = request.getParameter("kind");
        HashMap<String, MenuDTO> menuMap = MenuMap.menuMap;

        if(boardKind == null || menuMap.get(boardKind) == null ){
            request.setAttribute("status", 401);
            request.setAttribute("message", "잘못된 요청입니다");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error");

            dispatcher.forward(request, response);
            return false;
        }


        return true;
    }
}
