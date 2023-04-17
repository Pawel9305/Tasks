package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "test_content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getContent(), taskDto.getContent());
    }

    @Test
    void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test", "test_content");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(task.getContent(), taskDto.getContent());
    }

    @Test
    void testMapToTaskDtoList() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "test", "test_content"));

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals(taskList.get(0).getId(), taskDtos.get(0).getId());
        assertEquals(taskList.get(0).getContent(), taskDtos.get(0).getContent());
        assertEquals(taskList.get(0).getTitle(), taskDtos.get(0).getTitle());
    }
}
