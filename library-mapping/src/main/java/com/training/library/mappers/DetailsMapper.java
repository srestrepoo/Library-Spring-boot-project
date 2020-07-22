package com.training.library.mappers;

import com.training.library.dtos.HistoryDetailsDto;
import com.training.library.dtos.MathDetailsDto;
import com.training.library.entities.Book;
import com.training.library.entities.HistoryDetails;
import com.training.library.entities.MathDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetailsMapper {

    @Mapping(source = "newBook", target = "book")
    @Mapping(target = "id", ignore = true)
    MathDetails dtoToMathDetails(MathDetailsDto mathDetailsDto, Book newBook);

    MathDetailsDto mathDetailsToDto(MathDetails mathDetails);

    @Mapping(source = "newBook", target = "book")
    @Mapping(target = "id", ignore = true)
    HistoryDetails dtoToHistoryDetails(HistoryDetailsDto historyDetailsDto, Book newBook);

    HistoryDetailsDto historyDetailsToDto(HistoryDetails historyDetailsDto);
}
