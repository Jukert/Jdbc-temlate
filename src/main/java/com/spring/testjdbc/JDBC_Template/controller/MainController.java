package com.spring.testjdbc.JDBC_Template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class MainController {

    @Autowired


    @GetMapping
    public String getAll(){
        return "asdad";
    }

}
