package com.training.library.Handlers.ExternalBooksHandlers;

import com.training.library.IBookService;
import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.ExternalDetailsDto;
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
import java.util.stream.Collectors;

@Component
public class AddExternalBooksHandler implements GenericHandler<Pair<AuthorDto, ExternalGeneralInfoDto>> {

    @Autowired
    private ExternalLibraryMapper externalLibraryMapper;

    @Autowired
    private IBookService bookService;

    @Override
    public Message<List<BookDto>> handle(Pair<AuthorDto, ExternalGeneralInfoDto> messagePair, MessageHeaders messageHeaders) {
        List<BookDto> bookDtoList = messagePair.getRight().getLibros().stream()
                .map(externalBookDto -> externalLibraryMapper.externalBookDtoToBookDto(
                        externalBookDto, messagePair.getLeft(), messagePair.getRight().getFormato(), messagePair.getRight().getEditorial())
        ).collect(Collectors.toList());

        List<BookDto> createdBooks = bookService.createBookCopies(messagePair.getLeft().getId(), new ExternalDetailsDto(), bookDtoList);

        return MessageBuilder.withPayload(createdBooks).build();
    }
}
