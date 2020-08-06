package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
    @Transactional
    public List<BookDto> handleOrder(BookDto bookDto) {
        List<BookDto> bookDtoList = new ArrayList();
        if(bookDto.getState().equals(StateEnum.ACCEPTABLE)){
            bookDtoList.add(bookService.createBook(bookDto.toBuilder().id(null).state(StateEnum.EXCELLENT).build()));
        } else {
            for(int index = 0; index < 3; index++){
                bookDtoList.add(bookService.createBook(bookDto.toBuilder().id(null).state(StateEnum.EXCELLENT).build()));
            }
        }
        return bookDtoList;
    }

}
