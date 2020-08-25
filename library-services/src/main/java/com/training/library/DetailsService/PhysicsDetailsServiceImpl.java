package com.training.library.DetailsService;

import com.training.library.dtos.Details.DetailsDto;
import com.training.library.dtos.Details.PhysicsDetailsDto;
import com.training.library.entities.Book;
import com.training.library.entities.PhysicsDetails;
import com.training.library.exceptions.EntityNotFound;
import com.training.library.mappers.DetailsMapper;
import com.training.library.repositories.PhysicsDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicsDetailsServiceImpl implements IDetailsService {

    @Autowired
    private PhysicsDetailsRepository physicsDetailsRepository;

    @Autowired
    private DetailsMapper detailsMapper;

    @Override
    public DetailsDto createDetails(DetailsDto detailsDto, Book book) {
        PhysicsDetails physicsDetails = detailsMapper.dtoToPhysicsDetails((PhysicsDetailsDto) detailsDto, book);
        PhysicsDetails createdDetails = physicsDetailsRepository.save(physicsDetails);
        return detailsMapper.physicsDetailsToDto(createdDetails);
    }

    @Override
    public DetailsDto updateDetails(Integer bookId, Book updatedBook, DetailsDto detailsDto) {
        PhysicsDetails physicsDetails = physicsDetailsRepository.findById(bookId)
                .orElseThrow(EntityNotFound::new);
        detailsMapper.updatePhysicsDetails(physicsDetails, (PhysicsDetailsDto) detailsDto, updatedBook);
        physicsDetailsRepository.save(physicsDetails);
        return detailsMapper.physicsDetailsToDto(physicsDetails);
    }

    @Override
    public Class getBookDetailDtoClass() {
        return PhysicsDetailsDto.class;
    }
}
