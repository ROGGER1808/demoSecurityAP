package com.transon.securityDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //note - this is a spring-boot controller, not @RestController
public class HomeController {
    @RequestMapping("/swagger")
    public String home() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping("/")
    public String hello() {
        return "Welcome to here!";
    }
}

