package com.lucamanfroi.startuprush.services;


import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String helloworld(String name) {
        return "Hello World " + name;
    }
}
