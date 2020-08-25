package com.training.library.DetailsService;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DetailsServiceProxy {

    @Autowired
    List<IDetailsService> detailsServiceList;

    private Map<Class, IDetailsService> detailsServiceMap;

    @PostConstruct
    public void setupDetailsService() {
        this.detailsServiceMap = this.detailsServiceList.stream()
                .collect(Collectors.toMap(IDetailsService::getBookDetailDtoClass, Function.identity()));
    }

    public DetailsDto createDetails(DetailsDto detailsDto, Book book){
        return this.detailsServiceMap.get(detailsDto.getClass()).createDetails(detailsDto, book);
    }

    public DetailsDto updateDetails(Integer bookId, Book updatedBook, DetailsDto detailsDto){
        return this.detailsServiceMap.get(detailsDto.getClass()).updateDetails(bookId, updatedBook, detailsDto);
    }

}
