package com.example.demo.runner;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMap;
import com.example.demo.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartupApplicationRunner implements ApplicationRunner {

    private final MenuService menuService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        HashMap<String, MenuDTO> menuMap = MenuMap.menuMap;

        List<MenuDTO> allMenu = menuService.getAllMenu();

        for (MenuDTO menuDTO : allMenu) {
            String menuName = menuDTO.getName();
            menuMap.put(menuName,menuDTO);
        }
    }
}
