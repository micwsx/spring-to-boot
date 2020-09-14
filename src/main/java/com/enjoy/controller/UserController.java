package com.enjoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public String info(){
        return "Hello";
    }


    @RequestMapping("/json")
    @ResponseBody
    public Map<String,String> json(){
        Map<String ,String> map=new HashMap<>();
        map.put("name", "Michael");
        return map;
    }
}
