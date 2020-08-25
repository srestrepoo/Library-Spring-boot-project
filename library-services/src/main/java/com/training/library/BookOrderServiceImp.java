package com.training.library;

import com.training.library.dtos.BookOrder.BookOrderDto;
import com.training.library.entities.BookOrder;
import com.training.library.mappers.BookOrderMapper;
import com.training.library.repositories.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookOrderServiceImp implements IBookOrderService {

    @Autowired
    private BookOrderRepository bookOrderRepository;

    @Autowired
    private BookOrderMapper bookOrderMapper;

    @Override
    public BookOrderDto createBookOrder(BookOrderDto bookOrderDto) {
        BookOrder bookOrder = bookOrderMapper.dtoToBookOrder(bookOrderDto);
        return bookOrderMapper.BookOrderToDto(bookOrderRepository.save(bookOrder));
    }

}
