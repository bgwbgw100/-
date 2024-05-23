package com.example.demo.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;

    public List<MenuDTO> getAllMenu(){

        return menuMapper.selectMenuAll();
    }
}
