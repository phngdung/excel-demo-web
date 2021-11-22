package com.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        System.out.print("Greetings from Spring Boot!");
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/boys")
    public String getBoy() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public String custom() {
        return "custom";
    }

}