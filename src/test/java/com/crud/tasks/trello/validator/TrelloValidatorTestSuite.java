package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrelloValidatorTestSuite {

    @InjectMocks
    private TrelloValidator trelloValidator;

    @Test
    void testValidateTrelloBoardsOnlyTestBoardExcluding() {
        //Given
        List<TrelloBoard> trelloBoardList = List.of(new TrelloBoard("1", "test", List.of()));

        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(trelloBoardList);

        //Then
        assertEquals(0, filteredList.size());
    }

    @Test
    void testValidateTrelloBoardsReturnOneBoard() {
        //Given
        List<TrelloBoard> trelloBoards = List.of(new TrelloBoard("1", "new_board", List.of()));

        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(1, filteredList.size());
        assertEquals(trelloBoards.get(0).getId(), filteredList.get(0).getId());
        assertEquals(trelloBoards.get(0).getName(), filteredList.get(0).getName());
        assertTrue(filteredList.get(0).getLists().isEmpty());
    }
}
