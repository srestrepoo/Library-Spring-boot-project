package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorDto.AuthorDtoBuilder;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.dtos.AuthorViewDto.AuthorViewDtoBuilder;
import com.training.library.entities.Author;
import com.training.library.entities.Author.AuthorBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-03T14:19:36-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        AuthorBuilder author = Author.builder();

        author.id( authorDto.getId() );
        author.name( authorDto.getName() );
        author.nationality( authorDto.getNationality() );
        author.nativeLanguage( authorDto.getNativeLanguage() );

        return author.build();
    }

    @Override
    public AuthorDto authorToAuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.id( author.getId() );
        authorDto.name( author.getName() );
        authorDto.nationality( author.getNationality() );
        authorDto.nativeLanguage( author.getNativeLanguage() );

        return authorDto.build();
    }

    @Override
    public AuthorViewDto authorToAuthorViewDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorViewDtoBuilder authorViewDto = AuthorViewDto.builder();

        authorViewDto.id( author.getId() );
        authorViewDto.name( author.getName() );
        authorViewDto.nationality( author.getNationality() );
        authorViewDto.nativeLanguage( author.getNativeLanguage() );

        return authorViewDto.build();
    }

    @Override
    public void updateAuthorFromDto(AuthorDto authorDto, Author author) {
        if ( authorDto == null ) {
            return;
        }

        author.setName( authorDto.getName() );
        author.setNationality( authorDto.getNationality() );
        author.setNativeLanguage( authorDto.getNativeLanguage() );
    }
}
