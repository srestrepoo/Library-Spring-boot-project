package com.training.library.DetailsService;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.dtos.Details.ExternalDetailsDto;
import com.training.library.entities.Book;
import com.training.library.entities.ExternalDetails;
import com.training.library.exceptions.CategoryConflictException;
import com.training.library.mappers.DetailsMapper;
import com.training.library.repositories.ExternalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalDetailsServiceImpl implements IDetailsService{

    @Autowired
    private ExternalDetailsRepository externalDetailsRepository;

    @Autowired
    private DetailsMapper detailsMapper;

    @Override
    public DetailsDto createDetails(DetailsDto detailsDto, Book book) {
        ExternalDetails externalDetails = detailsMapper.dtoToExternalDetails((ExternalDetailsDto) detailsDto, book);
        ExternalDetails createdDetails = externalDetailsRepository.save(externalDetails);
        return detailsMapper.externalDetailsToDto(createdDetails);
    }

    @Override
    public DetailsDto updateDetails(Integer bookId, Book updatedBook, DetailsDto detailsDto) {
        ExternalDetails externalDetails = externalDetailsRepository.findById(bookId)
                .orElseThrow(CategoryConflictException::new);
        detailsMapper.updateExternalDetails(externalDetails, (ExternalDetailsDto) detailsDto, updatedBook);
        externalDetailsRepository.save(externalDetails);
        return detailsMapper.externalDetailsToDto(externalDetails);
    }

    @Override
    public Class getBookDetailDtoClass() {
        return ExternalDetailsDto.class;
    }
}
