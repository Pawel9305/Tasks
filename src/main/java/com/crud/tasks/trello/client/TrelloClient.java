package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    private final TrelloConfig config;

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getTrelloApiEndpoint() + "/members/" + config.getTrelloUsername() + "/boards")
                .queryParam("key", config.getTrelloAppKey())
                .queryParam("token", config.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()))
                    .toList();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }


    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(config.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", config.getTrelloAppKey())
                .queryParam("token", config.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }
}
