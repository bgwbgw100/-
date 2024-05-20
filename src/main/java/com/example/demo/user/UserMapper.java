package com.example.demo.user;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT id,password,login_try as loginTry,power FROM USER WHERE id  = #{user.id}")
    public User findById(@Param("user") User user);

}
