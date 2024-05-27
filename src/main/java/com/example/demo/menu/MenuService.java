package com.example.demo.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;

    public List<MenuDTO> getAllMenu(){

        return menuMapper.selectMenuAll();
    }


    public void updateMenuMap(){
        HashMap<String, MenuDTO> menuMap = MenuMap.menuMap;
        menuMap.clear();

        List<MenuDTO> allMenu = getAllMenu();
        for (MenuDTO menuDTO : allMenu) {
            String menuName = menuDTO.getName();
            menuMap.put(menuName,menuDTO);
        }
    }
}
