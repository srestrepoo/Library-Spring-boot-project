package com.training.library.DetailsService;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.dtos.Details.HistoryDetailsDto;
import com.training.library.entities.Book;
import com.training.library.entities.HistoryDetails;
import com.training.library.exceptions.CategoryConflictException;
import com.training.library.mappers.DetailsMapper;
import com.training.library.repositories.HistoryDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryDetailsServiceImpl implements IDetailsService{

    @Autowired
    private HistoryDetailsRepository historyDetailsRepository;

    @Autowired
    private DetailsMapper detailsMapper;

    @Override
    public DetailsDto createDetails(DetailsDto detailsDto, Book book) {
        HistoryDetails historyDetails = detailsMapper.dtoToHistoryDetails((HistoryDetailsDto) detailsDto, book);
        HistoryDetails createdDetails = historyDetailsRepository.save(historyDetails);
        return detailsMapper.historyDetailsToDto(createdDetails);
    }

    @Override
    public DetailsDto updateDetails(Integer bookId, Book updatedBook, DetailsDto detailsDto) {
        HistoryDetails historyDetails = historyDetailsRepository.findById(bookId)
                .orElseThrow(CategoryConflictException::new);
        detailsMapper.updateHistoryDetails(historyDetails, (HistoryDetailsDto) detailsDto, updatedBook);
        historyDetailsRepository.save(historyDetails);
        return detailsMapper.historyDetailsToDto(historyDetails);
    }

    @Override
    public Class getBookDetailDtoClass() {
        return HistoryDetailsDto.class;
    }
}
