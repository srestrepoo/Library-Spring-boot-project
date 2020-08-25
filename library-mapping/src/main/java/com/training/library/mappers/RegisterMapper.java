package com.training.library.mappers;

import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;
import com.training.library.entities.Book;
import com.training.library.entities.Register;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {

    @Mapping(target = "book", source = "registerDto.bookDto")
    @Mapping(target = "bookOrder", source = "registerDto.bookOrderDto")
    Register dtoToRegister(RegisterDto registerDto);

    @Mapping(target = "bookId", source = "register.book.id")
    @Mapping(target = "bookOrderId", source = "register.bookOrder.id")
    RegisterViewDto registerToRegisterViewDto(Register register);

    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "detailsDto", ignore = true)
    BookDto bookToBookDto(Book book);

}
