package com.training.library.DetailsService;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.entities.Book;

public interface IDetailsService {

    DetailsDto createDetails(DetailsDto detailsDto, Book book);

    DetailsDto updateDetails(Integer bookId, Book updatedBook, DetailsDto detailsDto);

    Class getBookDetailDtoClass();


}
