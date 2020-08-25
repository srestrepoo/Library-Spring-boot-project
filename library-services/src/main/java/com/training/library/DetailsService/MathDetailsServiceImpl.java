package com.training.library.DetailsService;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.entities.Book;
import com.training.library.entities.MathDetails;
import com.training.library.exceptions.EntityNotFound;
import com.training.library.mappers.DetailsMapper;
import com.training.library.repositories.MathDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MathDetailsServiceImpl implements IDetailsService{

    @Autowired
    private MathDetailsRepository mathDetailsRepository;

    @Autowired
    private DetailsMapper detailsMapper;

    @Override
    public DetailsDto createDetails(DetailsDto detailsDto, Book book) {
        MathDetails mathDetails = detailsMapper.dtoToMathDetails((MathDetailsDto) detailsDto, book);
        MathDetails createdDetails = mathDetailsRepository.save(mathDetails);
        return detailsMapper.mathDetailsToDto(createdDetails);
    }

    @Override
    public DetailsDto updateDetails(Integer bookId, Book updatedBook, DetailsDto detailsDto) {
        MathDetails mathDetails = mathDetailsRepository.findById(bookId)
                .orElseThrow(EntityNotFound::new);
        detailsMapper.updateMathDetails(mathDetails, (MathDetailsDto) detailsDto, updatedBook);
        mathDetailsRepository.save(mathDetails);
        return detailsMapper.mathDetailsToDto(mathDetails);
    }

    @Override
    public Class getBookDetailDtoClass() {
        return MathDetailsDto.class;
    }
}
