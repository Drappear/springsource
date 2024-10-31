package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.todo.dto.TodoDTO;
import com.example.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/todo")
@RequiredArgsConstructor
@Controller
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/list")
    public void getList(Boolean completed, Model model) {
        log.info("todo list page");

        List<TodoDTO> todoDtoList = todoService.getList(completed);

        model.addAttribute("todoDtoList", todoDtoList);
    }

    @GetMapping("/read")
    public void getRead(@RequestParam Long id, Model model) {
        log.info("todo read page, id : {}", id);
        TodoDTO todoDto = todoService.getRow(id);
        model.addAttribute("todoDto", todoDto);
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("get todo create page");
    }

    @PostMapping("/create")
    public String postCreate(TodoDTO todoDto) {
        log.info("post todo create, dto : {}", todoDto);
        todoService.create(todoDto);
        return "redirect:/todo/list";
    }

    @PostMapping("/update")
    public String postUpdate(TodoDTO todoDto, RedirectAttributes rttr) {
        log.info("post todo update, dto : {}", todoDto);
        todoService.updateCompleted(todoDto);
        rttr.addAttribute("id", todoDto.getId());
        return "redirect:/todo/read";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam Long id) {
        log.info("todo delete, id : {}", id);
        todoService.deleteRow(id);
        return "redirect:/todo/list";
    }

}
