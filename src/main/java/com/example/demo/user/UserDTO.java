package com.example.demo.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Date;


@Getter
@Setter
@Validated
public class UserDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 6, max=20, message = "아이디는 6자이상 20자 이하만 사용가능합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "어이디는 숫자와 영어만 가능합니다.")
    private String id;
    @NotBlank(message = "비밀번호를 입력하세요")
    @Size(min = 6,max = 20, message = "비밀번호는 6자이상 20자 이하입니다.")
    private String password;
    @NotBlank(message = "이름을 입력하세요")
    @Size(max = 255, message = "이름이 너무 깁니다.")
    private String name;
    //권한
    private String power;
    @NotBlank(message = "이메일을 입력하세요")
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String phone;
    private String deleteOx;
    private Date registDt;
    private Date deleteDt;
    private Date lastLogin;
    private int loginTry;
}
