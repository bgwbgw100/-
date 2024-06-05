package com.example.demo.aop;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Aspect
@Component
public class HeaderDataAspect {

    @After("execution(* com.example.demo.board..*Controller.*(..)) ")
    public void addHeaderBoard(JoinPoint joinPoint) {
        addModelMenuList(joinPoint);
    }
    @After("execution(* com.example.demo.common..*Controller.*(..))")
    public void addHeaderCommon(JoinPoint joinPoint) {
        addModelMenuList(joinPoint);
    }


    @After("execution(* com.example.demo.user..*Controller.*(..))")
    public void addHeaderUser(JoinPoint joinPoint) {
        addModelMenuList(joinPoint);
    }

    public List<MenuDTO> menuSetting(List<MenuDTO> menuDTOS){
        return menuDTOS.stream().filter(menuDTO -> menuDTO.getStep()==1).toList();

    }

    private void addModelMenuList(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Model model = null;

        for (Object arg : args) {
            if (arg instanceof Model) {
                model = (Model) arg;
                break;
            }
        }

        if (model != null) {
            List<MenuDTO> menuDTOS = MenuMap.menuMap.values().stream().toList();
            menuDTOS = menuSetting(menuDTOS);
            model.addAttribute("_menuList", menuDTOS);
        }
    }


}
