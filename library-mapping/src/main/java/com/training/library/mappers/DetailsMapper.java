package com.training.library.mappers;

import com.training.library.dtos.Book.DetailBookViewDto;
import com.training.library.dtos.Details.ExternalDetailsDto;
import com.training.library.dtos.Details.HistoryDetailsDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.dtos.Details.PhysicsDetailsDto;
import com.training.library.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DetailsMapper {

    @Mapping(source = "newBook", target = "book")
    @Mapping(target = "id", ignore = true)
    MathDetails dtoToMathDetails(MathDetailsDto mathDetailsDto, Book newBook);

    MathDetailsDto mathDetailsToDto(MathDetails mathDetails);

    MathDetailsDto detailBookToMathDetailsDto(DetailBookViewDto detailBookViewDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "book")
    void updateMathDetails(@MappingTarget MathDetails mathDetails, MathDetailsDto mathDetailsDto, Book book);

    @Mapping(source = "newBook", target = "book")
    @Mapping(target = "id", ignore = true)
    HistoryDetails dtoToHistoryDetails(HistoryDetailsDto historyDetailsDto, Book newBook);

    HistoryDetailsDto historyDetailsToDto(HistoryDetails historyDetailsDto);

    HistoryDetailsDto detailBookToHistoryDetailsDto(DetailBookViewDto detailBookViewDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "book")
    void updateHistoryDetails(@MappingTarget HistoryDetails historyDetails, HistoryDetailsDto historyDetailsDto, Book book);

    @Mapping(source = "newBook", target = "book")
    @Mapping(target = "id", ignore = true)
    PhysicsDetails dtoToPhysicsDetails(PhysicsDetailsDto physicsDetailsDto, Book newBook);

    PhysicsDetailsDto physicsDetailsToDto(PhysicsDetails physicsDetails);

    PhysicsDetailsDto detailBookToPhysicsDetailsDto(DetailBookViewDto detailBookViewDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "book")
    void updatePhysicsDetails(@MappingTarget PhysicsDetails physicsDetails, PhysicsDetailsDto physicsDetailsDto, Book book);


    @Mapping(source = "newBook", target = "book")
    @Mapping(target = "id", ignore = true)
    ExternalDetails dtoToExternalDetails(ExternalDetailsDto externalDetailsDto, Book newBook);

    ExternalDetailsDto externalDetailsToDto(ExternalDetails physicsDetails);

    ExternalDetailsDto detailBookToExternalDetailsDto(DetailBookViewDto detailBookViewDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "book", source = "book")
    void updateExternalDetails(@MappingTarget ExternalDetails externalDetails, ExternalDetailsDto externalDetailsDto, Book book);
}
