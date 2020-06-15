package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorDto.AuthorDtoBuilder;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookDto.BookDtoBuilder;
import com.training.library.entities.Author;
import com.training.library.entities.Author.AuthorBuilder;
import com.training.library.entities.Book;
import com.training.library.entities.Book.BookBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-12T16:35:21-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public Book bookDtoToBook(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookBuilder book = Book.builder();

        book.author( authorDtoToAuthor( bookDto.getAuthor() ) );
        book.id( bookDto.getId() );
        book.title( bookDto.getTitle() );
        book.editorial( bookDto.getEditorial() );
        book.year( bookDto.getYear() );
        book.pages( bookDto.getPages() );
        book.language( bookDto.getLanguage() );
        book.format( bookDto.getFormat() );

        return book.build();
    }

    @Override
    public BookDto bookToBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDtoBuilder bookDto = BookDto.builder();

        bookDto.author( authorToAuthorDto( book.getAuthor() ) );
        bookDto.id( book.getId() );
        bookDto.title( book.getTitle() );
        bookDto.editorial( book.getEditorial() );
        bookDto.year( book.getYear() );
        bookDto.pages( book.getPages() );
        bookDto.language( book.getLanguage() );
        bookDto.format( book.getFormat() );

        return bookDto.build();
    }

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
}
