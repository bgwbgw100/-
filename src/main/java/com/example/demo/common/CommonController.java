package com.example.demo.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {


    @GetMapping("home")
    public String home(Model model){
        return "index";
    }

    @GetMapping("index")
    public String index(Model model){
        return "index";
    }

    @GetMapping("")
    public String defaultPage(Model model){
        return "index";
    }

}
