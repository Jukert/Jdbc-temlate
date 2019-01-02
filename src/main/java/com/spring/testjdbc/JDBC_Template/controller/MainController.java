package com.spring.testjdbc.JDBC_Template.controller;

import com.spring.testjdbc.JDBC_Template.common.Product;
import com.spring.testjdbc.JDBC_Template.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class MainController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getAll(){
        productService.insert(new Product(20,"asda",123));
        return "asdad";
    }

}
