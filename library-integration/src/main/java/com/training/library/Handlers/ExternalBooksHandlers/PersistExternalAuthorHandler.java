package com.training.library.Handlers.ExternalBooksHandlers;

import com.training.library.IAuthorService;
import com.training.library.IExternalLibraryService;
import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Author.FilterAuthorDto;
import com.training.library.dtos.ExternalLibrary.ExternalAuthorDto;
import com.training.library.dtos.ExternalLibrary.ExternalAuthorFilterDto;
import com.training.library.dtos.ExternalLibrary.ExternalGeneralInfoDto;
import com.training.library.mappers.ExternalLibraryMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersistExternalAuthorHandler implements GenericHandler<ExternalGeneralInfoDto> {

    @Autowired
    private IExternalLibraryService externalLibraryService;

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private ExternalLibraryMapper externalLibraryMapper;

    @Override
    public Message<Pair<AuthorDto, ExternalGeneralInfoDto>> handle(ExternalGeneralInfoDto externalGeneralInfoDto, MessageHeaders messageHeaders) {

        ExternalAuthorDto externalAuthorDto = externalLibraryService.getExternalAuthor(
                messageHeaders.get("externalToken").toString(),
                ExternalAuthorFilterDto.builder().nombre(externalGeneralInfoDto.getAutor()).build());

        AuthorDto authorDto = externalLibraryMapper.externalAuthorDtoToAuthorDto(externalAuthorDto);

        FilterAuthorDto filterAuthorDto = FilterAuthorDto.builder().name(authorDto.getName())
                .nationality(authorDto.getNationality()).nativeLanguage(authorDto.getNativeLanguage()).maxResults(1).build();

        List<AuthorDto> authorResult = authorService.getAllAuthors(filterAuthorDto);

        AuthorDto persistedAuthorDto = (authorResult.size() > 0)? authorResult.get(0) : authorService.createAuthor(authorDto);

        return MessageBuilder
                .withPayload(Pair.of(persistedAuthorDto, externalGeneralInfoDto))
                .copyHeaders(messageHeaders)
                .build();
    }
}
