package com.example.demo.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {


    private final ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, WebRequest webRequest, Model model, HttpServletResponse response) {
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        errorDetails.get("status");

        Integer status = (Integer) errorDetails.get("status");

        response.setStatus(status);

        if(status == 404) {

            model.addAttribute("message", "요청하신 페이지를 찾을 수 없습니다.");

        }else if(status == 500){
            model.addAttribute("message","서버에 문제가 있습니다 관리자에게 문의하세요");
        }else {
           model.addAttribute("message", errorDetails.get("message"));
        }

        model.addAttribute("status", errorDetails.get("status"));

        model.addAttribute("error", errorDetails.get("error"));

        if(request.getAttribute("status") != null){

            model.addAttribute("status", request.getAttribute("status"));
            response.setStatus(Integer.parseInt(request.getAttribute("status").toString()));

        }
        if(request.getAttribute("message") != null){
            model.addAttribute("message", request.getAttribute("message"));
        }


        return "error";
    }


}