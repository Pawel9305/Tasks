package com.crud.tasks.trello.service;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TrelloServiceTest {

    @Autowired
    private TrelloService trelloService;

    @MockBean
    private TrelloClient trelloClient;

    @Test
    void shouldReturnEmptyList() {
        //Given
        List<TrelloBoardDto> emptyList = List.of();
        when(trelloService.fetchTrelloBoards()).thenReturn(emptyList);

        //When
        List<TrelloBoardDto> resultList = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(0, resultList.size());
    }

    @Test
    void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDtos = List.of(new TrelloListDto("23", "test_list", false));
        List<TrelloBoardDto> trelloBoardDtos = List.of(new TrelloBoardDto("131", "test_name", List.of()));
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, trelloBoards.size());
        assertEquals("131", trelloBoards.get(0).getId());
        assertEquals("test_name", trelloBoards.get(0).getName());
    }
}
