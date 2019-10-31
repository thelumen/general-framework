package com.ri.generalFramework.controller;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestUserServerController {

    @Autowired
    Tracer tracer;

    @GetMapping("/hi")
    public String sayHi(String name) {
        tracer.currentSpan().tag("result", "Hi " + name);
        return "Hi " + name;
    }
}
