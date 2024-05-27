package com.example.demo.admin;


import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMapper;
import com.example.demo.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

public class MenuServiceTest {

    @Autowired
    AdminService adminService;
    @Autowired
    MenuMapper menuMapper;


    @Test
    @Transactional
    @Rollback
    void addTestService(){
        MenuDTO param = new MenuDTO();

        param.setName("test");
        param.setKind("BOARD");
        param.setLevel(0);
        param.setKorName("테스트");

        adminService.addMenu(param);
        MenuDTO selectMenu = menuMapper.selectOneMenu(param);
        assertThat(param.getMenuNumber()).isEqualTo(selectMenu.getMenuNumber());
        assertThat(param.getName()).isEqualTo(selectMenu.getName());
        assertThat(param.getKind()).isEqualTo(selectMenu.getKind());
        assertThat(param.getKorName()).isEqualTo(selectMenu.getKorName());
        assertThat(param.getLevel()).isEqualTo(selectMenu.getLevel());
    }

    @Test
    @Transactional
    @Rollback
    void updateTestService(){
        MenuDTO add = new MenuDTO();

        add.setName("test");
        add.setKind("BOARD");
        add.setLevel(0);
        add.setKorName("테스트");
        adminService.addMenu(add);

        MenuDTO param = new MenuDTO();
        param.setMenuNumber(add.getMenuNumber());
        param.setName("testUpdate");
        param.setKorName("테스트업데이트");
        adminService.updateMenu(param);

        MenuDTO updateDTO = menuMapper.selectOneMenu(param);

        assertThat(param.getMenuNumber()).isEqualTo(updateDTO.getMenuNumber());
        assertThat(param.getName()).isEqualTo(updateDTO.getName());
        assertThat(param.getKorName()).isEqualTo(updateDTO.getKorName());

    }

    @Test
    @Transactional
    @Rollback
    void deleteTestService(){

        MenuDTO add = new MenuDTO();

        add.setName("test");
        add.setKind("BOARD");
        add.setLevel(0);
        add.setKorName("테스트");
        adminService.addMenu(add);

        MenuDTO param = new MenuDTO();
        param.setMenuNumber(add.getMenuNumber());

        adminService.deleteMenu(param);

        MenuDTO delete = menuMapper.selectOneMenu(param);

        assertThat(delete).isNull();

    }


}
