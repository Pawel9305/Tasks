package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetEmptyList() throws Exception {
        //Given
        when(taskController.getTasks()).thenReturn(ResponseEntity.ok(List.of()));
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldReturnATask() throws Exception {
        //Given
        TaskDto mappedTask = new TaskDto(1L, "test_title", "test_content");
        when(taskController.getTasks()).thenReturn(ResponseEntity.ok(List.of(mappedTask)));

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content", Matchers.is("test_content")));
    }

    @Test
    void shouldThrowTaskNotFoundException() throws Exception {
        //Given
        when(taskController.getTask(1L)).thenThrow(new TaskNotFoundException());

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void shouldReturnOneTask() throws Exception {
        //Given
        when(taskController.getTask(1L)).thenReturn(ResponseEntity.ok(new TaskDto(1L,
                "test_title", "test_content")));

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test_content")));
    }

    @Test
    void shouldDeleteWork() throws Exception {
        //Given
        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateATask() throws Exception {
        //Given
        TaskDto updatedTaskDto = new TaskDto(1L, "updated_title", "updated_test_content");
        when(taskController.updateTask(any(TaskDto.class))).thenReturn(ResponseEntity.ok(updatedTaskDto));
        Gson gson = new Gson();
        String update = gson.toJson(updatedTaskDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updated_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updated_test_content")));

    }

    @Test
    void shouldAddATask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "new_task", "new_content");
        when(taskController.createTask(any(TaskDto.class))).thenReturn(ResponseEntity.ok(taskDto));
        Gson gson = new Gson();
        String newTask = gson.toJson(taskDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(newTask))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("new_task")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("new_content")));
    }
}