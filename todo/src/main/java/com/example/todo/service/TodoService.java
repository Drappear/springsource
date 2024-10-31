package com.example.todo.service;

import java.util.List;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;

public interface TodoService {
    List<TodoDTO> getList(Boolean completed);

    TodoDTO getRow(Long id);

    TodoDTO create(TodoDTO dto);

    List<TodoDTO> getCompletedList();

    Long updateCompleted(TodoDTO todoDto);

    void deleteRow(Long id);

    // dto ==> entity
    public default Todo dtoToEntity(TodoDTO dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .completed(dto.getCompleted())
                .important(dto.getImportant())
                .build();
    }

    // entity ==> dto
    public default TodoDTO entityToDto(Todo entity) {
        return TodoDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .completed(entity.getCompleted())
                .important(entity.getImportant())
                .build();
    }
}
