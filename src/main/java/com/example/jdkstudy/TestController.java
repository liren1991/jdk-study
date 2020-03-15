package com.example.jdkstudy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping(value = "/login.html")
    public String index() {
        return "index.html";
    }
}
