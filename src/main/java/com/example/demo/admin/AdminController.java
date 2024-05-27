package com.example.demo.admin;

import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping( value = {"home","index",""})
    public String index(){
        return "admin/index";
    }

    @GetMapping("management/menu")
    public String menu(Model model){

        model.addAttribute("menuList" , adminService.getMenuAll());

        return "admin/management/menu";
    }

    @PostMapping("management/menu")
    @ResponseBody
    public ResponseEntity<String> createMenu(@RequestBody MenuDTO menuDTO){

        adminService.addMenu(menuDTO);

        return ResponseEntity.ok("success");
    }

    @PutMapping("management/menu")
    @ResponseBody
    public ResponseEntity<String> updateMenu(@RequestBody MenuDTO menuDTO){

        adminService.updateMenu(menuDTO);

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("management/menu")
    @ResponseBody
    public ResponseEntity<String> deleteMenu(@RequestBody MenuDTO menuDTO){

        adminService.deleteMenu(menuDTO);

        return ResponseEntity.ok("success");
    }



}
