package com.jetbrains.demo.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
class BaseController {
    @GetMapping("/health-check")
    public String checkHealth() {
        return "Hello, World!";
    }

    @GetMapping("/list")
    public List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        list.add("Four");

        return list;
    }
}
