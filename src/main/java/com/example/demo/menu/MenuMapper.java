package com.example.demo.menu;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper  {

    @Select("""
    SELECT *
    FROM menu
    """)
    List<MenuDTO> selectMenuAll();
}
