package dev.iratherfear.xodos_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/home/")
public class HelloController {
    
    @GetMapping
    public String getMethodName() {
        return new String("This is home page");
    }    
}
