package com.training.library.Handlers.ExternalBooksHandlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeleteObsoleteExternalBooksHandler implements GenericHandler<List<BookDto>> {

    @Autowired
    private IBookService bookService;

    @Override
    public Object handle(List<BookDto> bookList, MessageHeaders messageHeaders) {
        if(bookList.size() > 0){
            List<String> isbnList = bookList.stream().map(BookDto::getIsbn).collect(Collectors.toList());
            bookService.deleteObsoleteExternalBooks(isbnList);
        }
        return bookList;
    }
}
