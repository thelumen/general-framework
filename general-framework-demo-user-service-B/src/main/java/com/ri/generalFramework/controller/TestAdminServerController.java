package com.ri.generalFramework.controller;

import brave.Tracer;
import com.ri.generalFramework.util.TraceIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testAdmin")
public class TestAdminServerController {

    @Autowired
    Tracer tracer;

    @GetMapping("/hi")
    public String sayHi(String name) {
        tracer.currentSpan().tag("result", "Hi " + name);
        return "Hi Admin " + name + " " + TraceIdUtils.getTraceId();
    }
}
