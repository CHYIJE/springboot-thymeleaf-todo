package com.example.my.domain.todo.controller;

import com.example.my.domain.todo.service.TodoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping({"", "/"}) // 슬레시가 없는 경로 있는 경로 둘다 와 (배열경로)
    public ModelAndView todoTablePage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("todo/table");
        return modelAndView;
    }

}
