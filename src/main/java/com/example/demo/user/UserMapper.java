package com.example.demo.user;


import com.example.demo.util.CommonPagingDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

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

    @Select("""
            <script>
            SELECT  id
                    ,name
                    ,email
                    ,delete_ox as deleteOx
                    ,delete_dt as deleteDt
                    ,regist_dt as registDt
                    ,last_login as lastLogin
                ,power
            FROM user
            WHERE 1=1
            </script>
    """)
    List<UserDTO> selectAllUser(@Param("user") UserDTO userDTO, @Param("page")CommonPagingDTO commonPagingDTO);


    @Update("""
        UPDATE user
        SET    power = #{power}
        WHERE id = #{id}
    """)
    void updatePower(UserDTO userDTO);

    @Update("""
        UPDATE user
        SET    password = #{password}
        WHERE id = #{id}
    """)
    void updatePassword(UserDTO userDTO);
}
