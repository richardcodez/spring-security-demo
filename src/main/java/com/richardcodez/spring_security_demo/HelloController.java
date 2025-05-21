package com.richardcodez.spring_security_demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String great(HttpServletRequest request){
        return "Hello World: " + request.getSession().getId();
    }

    @GetMapping("about")
    public String about(HttpServletRequest request){
        return "Richardcodez: " + request.getSession().getId();
    }

}
