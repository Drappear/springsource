package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public List<TodoDTO> getList(Boolean completed) {

        // List<Todo> todoEntityList = todoRepository.findAll();

        // List<TodoDTO> todoDtoList = new ArrayList<>();
        // todoEntityList.forEach(entity -> {
        // todoDtoList.add(entityToDto(entity));
        // });

        // 전체 todo
        // List<TodoDTO> todoDtoList = todoEntityList
        // .stream()
        // .map(todoEntity -> entityToDto(todoEntity))
        // .collect(Collectors.toList());

        // 미완료 todo
        List<Todo> result = todoRepository.findByCompleted(completed);
        List<TodoDTO> list = result.stream().map(todo -> entityToDto(todo)).collect(Collectors.toList());

        return list;
    }

    @Override
    public TodoDTO getRow(Long id) {
        Todo todoEntity = todoRepository.findById(id).get();

        return entityToDto(todoEntity);
    }

    @Override
    public TodoDTO create(TodoDTO dto) {
        Todo todoEntity = dtoToEntity(dto);
        return entityToDto(todoRepository.save(todoEntity));
    }

    @Override
    public List<TodoDTO> getCompletedList() {
        return null;
    }

    @Override
    public Long updateCompleted(TodoDTO todoDto) {
        Todo todoEntity = todoRepository.findById(todoDto.getId()).get();
        todoEntity.setCompleted(todoDto.getCompleted());
        Todo updateTodoEntity = todoRepository.save(todoEntity);
        return updateTodoEntity.getId();
    }

    @Override
    public void deleteRow(Long id) {
        todoRepository.deleteById(id);
    }

}
