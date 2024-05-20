package com.example.demo.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {


    @GetMapping("home")
    public String home(){
        return "index";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }

}
