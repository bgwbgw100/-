package com.example.demo.admin.menu;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuStorageMapper {
    @Select("""
        SELECT menu_code as menuCode
               ,menu
               ,step
               ,child_count as childCount
        FROM menu_storage
    """)
    public List<MenuStorageDTO> selectMenuStorageAll();

}
