package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("test_board1", "1",
                new ArrayList<>());
        trelloBoardDto.getLists().add(new TrelloListDto("1", "test_list", true));
        trelloBoardsDto.add(trelloBoardDto);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        TrelloBoard trelloBoard = trelloBoards.get(0);

        //Then
        assertEquals(trelloBoardsDto.size(), trelloBoards.size());
        assertEquals(trelloBoardDto.getId(), trelloBoard.getId());
        assertEquals(trelloBoardDto.getName(), trelloBoard.getName());
        assertEquals(trelloBoardDto.getLists().get(0).getId(), trelloBoard.getLists().get(0).getId());
        assertTrue(trelloBoard.getLists().get(0).isClosed());
    }

    @Test
    void testMapToBoardsDto() {
        //Given
        TrelloBoard trelloBoard = new TrelloBoard("1", "test_board", new ArrayList<>());
        trelloBoard.getLists().add(new TrelloList("1", "test_list", false));
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(trelloBoard);

        //When
        List<TrelloBoardDto> trelloBoardsDto = trelloMapper.mapToBoardsDto(boards);
        TrelloBoardDto trelloBoardDto = trelloBoardsDto.get(0);

        //Then
        assertEquals(trelloBoard.getId(), trelloBoardDto.getId());
        assertEquals(trelloBoard.getName(), trelloBoardDto.getName());
        assertEquals(trelloBoard.getLists().get(0).getId(), trelloBoardDto.getLists().get(0).getId());
        assertFalse(trelloBoardDto.getLists().get(0).isClosed());
    }

    @Test
    void testMapToList() {
        //Given
        List<TrelloListDto> listDtos = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("1", "test_list", true);
        listDtos.add(trelloListDto);

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(listDtos);
        TrelloList trelloList = trelloLists.get(0);

        //Then
        assertEquals(trelloListDto.getId(), trelloList.getId());
        assertEquals(trelloListDto.getName(), trelloList.getName());
        assertTrue(trelloList.isClosed());
    }

    @Test
    void testMapToDtoList() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        TrelloList trelloList = new TrelloList("1", "test_list", false);
        trelloLists.add(trelloList);

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToDtoList(trelloLists);
        TrelloListDto trelloListDto = trelloListDtos.get(0);

        //Then
        assertEquals(trelloList.getId(), trelloListDto.getId());
        assertEquals(trelloList.getName(), trelloListDto.getName());
        assertFalse(trelloListDto.isClosed());
    }

    @Test
    void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test_card", "test_description", "top", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    @Test
    void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_card", "test_desc", "bottom", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }

}
