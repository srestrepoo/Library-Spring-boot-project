package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateMathOrderHandler implements ICreateOrderHandler {

    @Autowired
    private IBookService bookService;

    @Override
    public Class getBookDetailClass() {
        return MathDetailsDto.class;
    }

    @Override
    public List<BookDto> handleOrder(BookDto bookDto) {
        List<BookDto> bookDtoList = new ArrayList<>();

        Integer numberOfCopies = (bookDto.getState().equals(StateEnum.BAD)) ? 3 : 1;

        for (int index = 0; index < numberOfCopies; index++) {
            bookDtoList.add(bookDto.toBuilder().id(null).state(StateEnum.EXCELLENT).build());
        }

        return bookService.createBookCopies(bookDto.getAuthorId(), bookDto.getDetailsDto(), bookDtoList);
    }

}
