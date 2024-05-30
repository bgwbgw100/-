package com.example.demo.admin.user;

import com.example.demo.user.UserDTO;
import com.example.demo.util.CommonPagingDTO;
import com.example.demo.util.CustomTwoReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/management/")
public class AdminUserController {

    private final AdminUserService adminUserService;



    @RequestMapping("user")
    public String user(@ModelAttribute("user")UserDTO userDTO, @ModelAttribute("page") CommonPagingDTO page, Model model){
        CustomTwoReturn<List<UserDTO>, CommonPagingDTO> twoReturn = adminUserService.getAllUser(userDTO,page);
        List<UserDTO> userList = twoReturn.getType1();

        model.addAttribute("userList",userList);


        return "admin/management/user";
    }


}
