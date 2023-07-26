package com.example.my.domain.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller // ctrl + alt + o (import 자동 정리)
public class AuthController {

    // @GetMapping("/auth/login")
    // public ModelAndView login() {
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("auth/login");
    //     return modelAndView;
    // }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

    // @GetMapping("/auth/join")
    // public ModelAndView join() {
    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("auth/join");
    //     return modelAndView;
    // }

    @GetMapping("/auth/join")
    public String join() {
        return "auth/join";
    }

    // @GetMapping("/auth/logout")
    // public ModelAndView logout(HttpSession session) {

    //     session.invalidate();

    //     ModelAndView modelAndView = new ModelAndView();
    //     modelAndView.setViewName("redirect:/auth/login");
    //     return modelAndView;
    // }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "auth/logout";
    }

}