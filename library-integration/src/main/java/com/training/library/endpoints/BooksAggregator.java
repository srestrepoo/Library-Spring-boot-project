package com.training.library.endpoints;

import com.training.library.dtos.Book.BookDto;
import org.springframework.integration.annotation.Aggregator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BooksAggregator {

    @Aggregator
    public List<BookDto> aggregate(List<List<BookDto>> lists) {
        return lists.stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(BookDto::getId))
                .collect(Collectors.toList());
    }

}
