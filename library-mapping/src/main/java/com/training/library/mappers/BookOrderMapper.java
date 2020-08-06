package com.training.library.mappers;

import com.training.library.dtos.BookOrder.BookOrderDto;
import com.training.library.entities.BookOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookOrderMapper {

    BookOrder dtoToBookOrder(BookOrderDto bookOrderDto);

    BookOrderDto BookOrderToDto(BookOrder bookOrder);
}
