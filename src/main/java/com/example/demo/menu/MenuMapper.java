package com.example.demo.menu;

import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface MenuMapper  {

    @Select("""
      SELECT  B.name AS name
                ,B.menu_number AS menuNumber
                ,B.parent_number AS parentNumber
                ,B.level AS level
                ,B.kor_name AS korName
                ,B.kind AS kind
                ,A.step AS step
                ,A.child_count AS childCount
        FROM    menu_storage A,(SELECT *
                                FROM menu ) B
        WHERE A.menu = B.kind
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

    @Select("""
    SELECT menu_number as menuNumber
           ,name
           ,parent_number as parentNumber
           ,level
           ,kor_name as korName
           ,kind
    FROM menu
    WHERE kind = #{kind}
    """)
    List<MenuDTO> selectMenuByKind(@Param("kind") String kind);

    @Select("""
    SELECT menu_number as menuNumber
           ,name
           ,parent_number as parentNumber
           ,level
           ,kor_name as korName
           ,kind
    FROM menu
    WHERE parent_number = #{menuNumber}
    """)
    List<MenuDTO> selectChildMenu(@Param("menuNumber") int menuNumber);


}
