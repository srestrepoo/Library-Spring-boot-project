package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.dtos.BookDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper( AuthorMapper.class );

    Author authorDtoToAuthor(AuthorDto authorDto);

    AuthorDto authorToAuthorDto(Author author);

    default AuthorViewDto authorToAuthorViewDto(Author author) {
        if ( author == null ) {
            return null;
        }
        Long nativeBooks = author.getBooks().stream()
                .filter(book -> book.getLanguage() != null)
                .filter(book -> book.getLanguage().equals(author.getNativeLanguage()))
                .count();

        AuthorViewDto.AuthorViewDtoBuilder authorViewDto = AuthorViewDto.builder();

        authorViewDto.id( author.getId() );
        authorViewDto.name( author.getName() );
        authorViewDto.nationality( author.getNationality() );
        authorViewDto.nativeLanguage( author.getNativeLanguage() );
        authorViewDto.booksNumber(author.getBooks().size());
        authorViewDto.nativeLanguageBooks(Math.toIntExact(nativeBooks));
        authorViewDto.foreignLanguageBooks(author.getBooks().size() - Math.toIntExact(nativeBooks));

        return authorViewDto.build();
    };

    @Mapping(ignore = true, target = "author")
    Book bookDtoToBook(BookDto bookDto);

    @Mapping(ignore = true, target = "author")
    BookDto bookToBookDto(Book book);

}
