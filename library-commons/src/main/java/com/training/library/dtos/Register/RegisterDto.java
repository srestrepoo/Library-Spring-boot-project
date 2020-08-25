package com.training.library.dtos.Register;

import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.BookOrder.BookOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {

    private Integer id;
    private BookDto bookDto;
    private BookOrderDto bookOrderDto;

}
