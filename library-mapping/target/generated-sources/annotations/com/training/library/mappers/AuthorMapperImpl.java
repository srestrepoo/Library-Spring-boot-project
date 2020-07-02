package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorDto.AuthorDtoBuilder;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.dtos.AuthorViewDto.AuthorViewDtoBuilder;
import com.training.library.entities.Author;
import com.training.library.entities.Author.AuthorBuilder;
import com.training.library.enums.Language;
import com.training.library.enums.Nationality;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-02T14:51:49-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        AuthorBuilder author = Author.builder();

        if ( authorDto.getNationality() != null ) {
            author.nationality( authorDto.getNationality().name() );
        }
        if ( authorDto.getNativeLanguage() != null ) {
            author.nativeLanguage( authorDto.getNativeLanguage().name() );
        }
        author.id( authorDto.getId() );
        author.name( authorDto.getName() );

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
        if ( author.getNationality() != null ) {
            authorDto.nationality( Enum.valueOf( Nationality.class, author.getNationality() ) );
        }
        if ( author.getNativeLanguage() != null ) {
            authorDto.nativeLanguage( Enum.valueOf( Language.class, author.getNativeLanguage() ) );
        }

        return authorDto.build();
    }

    @Override
    public AuthorViewDto authorToAuthorViewDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorViewDtoBuilder authorViewDto = AuthorViewDto.builder();

        authorViewDto.nationality( author.getNationality() );
        authorViewDto.nativeLanguage( author.getNativeLanguage() );
        authorViewDto.id( author.getId() );
        authorViewDto.name( author.getName() );

        return authorViewDto.build();
    }
}
