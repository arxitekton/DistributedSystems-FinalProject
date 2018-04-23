package com.ucu.edu.adminPanel.controller;


import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.ucu.edu.user.model.User;



@Controller
public class AdminPanelController {

	
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser(WebRequest request, User user) {
        return "user";
    }

}
