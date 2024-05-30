package com.example.demo.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DynamicMapper {


    @Select("""
        ${query}
    """)
    int intDynamicSelect(@Param("query") String query);
}
