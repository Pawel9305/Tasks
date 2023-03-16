package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "{taskId}")
    public TaskDto getTask(@PathVariable long taskId) {
        Task task = service.getById(taskId);
        return taskMapper.mapToTaskDto(task);
    }

    @DeleteMapping
    public void deleteTask(@PathVariable long taskId) {

    }

    @PutMapping
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Edited test content");
    }

    @PostMapping
    public void createTask(TaskDto taskDto) {

    }


}
