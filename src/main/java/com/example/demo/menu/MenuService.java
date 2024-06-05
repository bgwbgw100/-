package com.example.demo.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        menuSetting();
    }

    public void menuSetting(){
        HashMap<String, MenuDTO> menuMap = MenuMap.menuMap;
        List<MenuDTO> menuDTOS = new ArrayList<>();
        for (Map.Entry<String,MenuDTO> entry :menuMap.entrySet()){
            menuDTOS.add(entry.getValue());
        }
        List<MenuDTO> step1 = menuDTOS.stream().filter(menuDTO -> menuDTO.getStep() == 1).toList();
        step1.forEach(filterMenuDTO -> {
            for (MenuDTO menuDTO : menuDTOS) {
                if(menuDTO.getParentNumber() == filterMenuDTO.getMenuNumber()){
                    List<MenuDTO> childList ;
                    if(filterMenuDTO.getChildList() == null){
                        filterMenuDTO.setChildList(new ArrayList<>());
                    }

                    childList = filterMenuDTO.getChildList();

                    childList.add(menuDTO);
                };
            }
        });

        List<MenuDTO> step2 = menuDTOS.stream().filter(menuDTO -> menuDTO.getStep() == 2).toList();
        step2.forEach(filterMenuDTO -> {
            for (MenuDTO menuDTO : menuDTOS) {
                if(menuDTO.getParentNumber() == filterMenuDTO.getMenuNumber()){
                    List<MenuDTO> childList ;
                    if(filterMenuDTO.getChildList() == null){
                        filterMenuDTO.setChildList(new ArrayList<>());
                    }
                    childList = filterMenuDTO.getChildList();
                    childList.add(menuDTO);
                };
            }
        });
    }



}
