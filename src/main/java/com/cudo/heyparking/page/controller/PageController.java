package com.cudo.heyparking.page.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/page")
public class PageController {

    @GetMapping("/main")
    public String showMainPage() {
        return "/page/main/mainPage";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {
        request.setAttribute("error", e.getMessage());
        return "forward:/page/error";
    }
}