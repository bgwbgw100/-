package com.example.demo.admin.menu;

import com.example.demo.menu.MenuDTO;
import com.example.demo.util.CustomTwoReturn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    private final AdminMenuValidator adminMenuValidator;

    @GetMapping( value = {"home","index",""})
    public String index(){
        return "admin/index";
    }

    @GetMapping("management/menu")
    public String menu(Model model){
        CustomTwoReturn<List<MenuDTO>, List<MenuStorageDTO>> menuAll = adminMenuService.getMenuAll();
        model.addAttribute("menuList" , menuAll.getType1());
        model.addAttribute("menuStorageList" , menuAll.getType2());

        return "admin/management/menu";
    }

    @PostMapping("management/menu")
    @ResponseBody
    public ResponseEntity<String> createMenu(@Valid @RequestBody MenuDTO menuDTO){

        if(!adminMenuValidator.menuInsertCheck(menuDTO)){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        };

        adminMenuService.addMenu(menuDTO);

        return ResponseEntity.ok("success");
    }

    @PutMapping("management/menu")
    @ResponseBody
    public ResponseEntity<String> updateMenu(@Valid @RequestBody MenuDTO menuDTO){

        adminMenuService.updateMenu(menuDTO);

        return ResponseEntity.ok("success");
    }

    @DeleteMapping("management/menu")
    @ResponseBody
    public ResponseEntity<String> deleteMenu(@RequestBody MenuDTO menuDTO){

        if(!adminMenuValidator.menuDeleteCheck(menuDTO)){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        adminMenuService.deleteMenu(menuDTO);

        return ResponseEntity.ok("success");
    }



}
