package com.example.demo.user;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT id,password,login_try as loginTry,power FROM USER WHERE id  = #{user.id}")
    public UserDTO findById(@Param("user") UserDTO userDTO);

    @Insert("""
            INSERT INTO user (id, password, name, power, email, phone, delete_ox, regist_dt)
            VALUES
            (#{user.id},#{user.password},#{user.name},'B',#{user.email},#{user.phone},'X',NOW())
            """)
    void insertUser(@Param("user") UserDTO userDTO);

    @Select("""
            SELECT COUNT(*)
            FROM user
            WHERE id = #{user.id}
            LIMIT 1
            """)
    int selectUserByIdCheck(@Param("user") UserDTO userDTO);

    @Select("""
            SELECT COUNT(*)
            FROM user
            WHERE email = #{user.email}
            LIMIT 1
            """)
    int selectUserByEmailCheck(@Param("user") UserDTO userDTO);


}
