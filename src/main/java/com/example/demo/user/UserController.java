package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserValidator userValidator;

    private final UserService userService;
    @GetMapping("login")
    public String login(){

        return "login";
    };
    @GetMapping("signup")
    public String signup(Model model){
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @PostMapping("signup")
    public String createSignup(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult,Model model){

        if(!userValidator.userDuplicationIdCheck(userDTO)){
            bindingResult.addError(new FieldError("userError","id","중복된 아이디입니다."));

        }
        if(!userValidator.userDuplicationEmailCheck(userDTO)){
            bindingResult.addError(new FieldError("userError","email","중복된 이메일입니다.."));
        }
        if(bindingResult.hasErrors()){
            return "signup";
        }

        userService.createUser(userDTO);


        return "redirect:/index";
    }

}
