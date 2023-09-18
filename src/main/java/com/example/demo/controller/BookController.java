package com.example.demo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping("/getBooks")
    public String getBooks() {
        System.out.println("springboot程序正在运行呢~");
        return "Hello,SpringBoot is running";
    }
}