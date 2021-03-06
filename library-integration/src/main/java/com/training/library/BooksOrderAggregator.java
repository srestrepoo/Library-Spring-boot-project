package com.training.library;

import com.training.library.dtos.Book.BookDto;
import org.springframework.integration.annotation.Aggregator;

import java.util.List;
import java.util.stream.Collectors;

public class BooksOrderAggregator {

    @Aggregator
    public List<BookDto> aggregate(List<List<BookDto>> lists) {
        return lists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
