package com.training.library.Handlers.ExternalBooksHandlers;

import com.training.library.ExternalBooksGateway;
import com.training.library.IBookService;
import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.ExternalDetailsDto;
import com.training.library.dtos.ExternalLibrary.ExternalGeneralInfoDto;
import com.training.library.mappers.BookMapper;
import com.training.library.mappers.ExternalLibraryMapper;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ExternalGeneralInfoHandler implements GenericHandler<Pair<AuthorDto, ExternalGeneralInfoDto>> {

    @Autowired
    private ExternalBooksGateway externalBooksGateway;

    @Autowired
    private IBookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ExternalLibraryMapper externalLibraryMapper;

    @Override
    public Message<List<BookDto>> handle(Pair<AuthorDto, ExternalGeneralInfoDto> messagePair, MessageHeaders messageHeaders) {
        List<BookDto> bookList = messagePair.getRight().getLibros().stream()
                .map(externalBookDto -> externalLibraryMapper.externalBookDtoToBookDto(
                        externalBookDto, messagePair.getLeft(), messagePair.getRight().getFormato(),
                        messagePair.getRight().getEditorial())).collect(Collectors.toList());

        List<BookDto> existentBookList = bookService.getBooksByIsbnList(
                bookList.stream().map(BookDto::getIsbn).collect(Collectors.toList()));

        List<Pair<BookDto, BookDto>> booksToUpdate = new ArrayList<>();
        List<BookDto> booksToInsert = new ArrayList<>();
        BookDto bookToUpdate;

        for (BookDto externalBook : bookList) {
            bookToUpdate = existentBookList.stream().filter(
                    bookDto -> bookDto.getIsbn().equals(externalBook.getIsbn()))
                    .findAny().orElse(null);

            if ((bookToUpdate == null)) {
                booksToInsert.add(externalBook);
            } else {
                booksToUpdate.add(Pair.of(bookToUpdate, externalBook));
            }
        }

        List<BookDto> insertedBooks = insertExternalBooks(messagePair.getLeft().getId(), booksToInsert);
        List<BookDto> updatedBooks = updateExternalBooks(messagePair.getLeft().getId(), booksToUpdate);

        return MessageBuilder.withPayload(
                Stream.of(insertedBooks, updatedBooks)
                        .flatMap(List::stream)
                        .collect(Collectors.toList())).build();
    }

    private List<BookDto> insertExternalBooks(Integer authorId, List<BookDto> bookList) {
        List<BookDto> createdBooks = new ArrayList<>();
        if (bookList.size() > 0) {
            createdBooks =
                    bookService.createBookCopies(authorId, new ExternalDetailsDto(), bookList);
        }
        return createdBooks;

    }

    private List<BookDto> updateExternalBooks(Integer authorId, List<Pair<BookDto, BookDto>> bookPairs) {
        List<BookDto> booksToUpdate = bookPairs.stream()
                .map(bookPair -> {
                    bookMapper.updateBookDtoFromDto(bookPair.getLeft(), bookPair.getRight());
                    return bookPair.getLeft();
                }).collect(Collectors.toList());

        return (booksToUpdate.size() > 0) ?
                bookService.updateBooksWithoutDetails(authorId, booksToUpdate) :
                new ArrayList<>();
    }
}
