package com.sparta.nuricalendaradvanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/check")
    public String checkPage() {
        return "check";
    }

}
