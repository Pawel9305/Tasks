package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;

    private final TaskMapper taskMapper;

    public List<TaskDto> getAllTasks() {
        return taskMapper.mapToTaskDtoList(repository.findAll());
    }

    public TaskDto getById(final long taskId) throws TaskNotFoundException {
        Task task = repository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        return taskMapper.mapToTaskDto(task);
    }

    public void saveTask(final TaskDto taskDto) {
        repository.save(taskMapper.mapToTask(taskDto));
    }

    public void deleteTask(final long taskId) {
        repository.deleteById(taskId);
    }
}
