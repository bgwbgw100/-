package com.example.demo.menu;

import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface MenuMapper  {

    @Select("""
    SELECT  menu_number as menuNumber
            ,name
            ,parent_number as parentNumber
            ,level
            ,kor_name as korName
            ,kind
    FROM menu
    """)
    List<MenuDTO> selectMenuAll();

    @Insert("""
    INSERT INTO menu(name, parent_number, level, kor_name, kind)
    SELECT  #{data.name} as name
            ,if(#{data.parentNumber} = 0 , NULL, #{data.parentNumber}) as parent_number
            ,if(#{data.parentNumber} = 0,0,(
                SELECT level+1
                FROM menu
                WHERE menu_number = #{data.parentNumber}
            )) as level
            ,#{data.korName} as kor_name
            ,#{data.kind} as kind
    """)
    @Options(useGeneratedKeys = true, keyProperty = "menuNumber")
    void insertMenu(@Param("data") MenuDTO menuDTO);

    @Update("""
    UPDATE menu
    SET name=#{data.name}
        ,kor_name = #{data.korName}
    WHERE menu_number = #{data.menuNumber}
    """)
    void updateMenu(@Param("data") MenuDTO menuDTO);

    @Delete("""
    DELETE FROM menu
    WHERE menu_number = #{data.menuNumber}
    """)
    void deleteMenu(@Param("data") MenuDTO menuDTO);

    @Select("""
    SELECT menu_number as menuNumber
           ,name
           ,parent_number as parentNumber
           ,level
           ,kor_name as korName
           ,kind
    FROM menu
    WHERE menu_number = #{data.menuNumber}
    """)
    MenuDTO selectOneMenu(@Param("data") MenuDTO menuDTO);

    @Select("""
    SELECT menu_number as menuNumber
           ,name
           ,parent_number as parentNumber
           ,level
           ,kor_name as korName
           ,kind
    FROM menu
    WHERE menu_number = #{parentNumber}
    """)
    MenuDTO selectOneMenuByParentNumber(@Param("parentNumber") int parentNumber);


}
