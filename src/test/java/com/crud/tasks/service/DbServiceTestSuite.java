package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DbServiceTestSuite {

    @Autowired
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private TaskRepository taskRepository;


    @Test
    void testDbServiceGetAllTasksEmptyList() {
        //Given
        when(dbService.getAllTasks()).thenReturn(List.of());

        //When
        List<TaskDto> fetchedList = dbService.getAllTasks();

        //Then
        assertEquals(0, fetchedList.size());
    }

    @Test
    void testDbServiceGetAllTasks() {
        //Given
        when(dbService.getAllTasks()).thenReturn(List.of(new TaskDto(1L, "test_task", "test_content")));

        //When
        List<TaskDto> resultList = dbService.getAllTasks();

        //Then
        assertEquals(1, resultList.size());
    }

    @Test
    void testDbServiceGetById() throws TaskNotFoundException {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_task", "test_content");
        Task mappedTask = new Task(1L, "test_task", "test_content");
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(mappedTask));
        when(dbService.getById(anyLong())).thenReturn(taskDto);

        //When
        TaskDto resultDto = dbService.getById(1);

        //Then
        assertEquals(taskDto.getId(), resultDto.getId());
        assertEquals(taskDto.getTitle(), resultDto.getTitle());
    }

    @Test
    void testDbServiceSaveTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_task", "test_content");
        Task mappedTask = new Task(1L, "test_task", "test_content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(mappedTask);

        //When
        dbService.saveTask(taskDto);

        //Then
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testDbServiceDeleteTask() {
        //Given
        //When
        dbService.deleteTask(1L);

        //Then
        verify(taskRepository, times(1)).deleteById(any());

    }
}
