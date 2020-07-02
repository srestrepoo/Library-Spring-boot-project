package com.training.library.mappers;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookDto.BookDtoBuilder;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.BookViewDto.BookViewDtoBuilder;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import com.training.library.entities.Book.BookBuilder;
import com.training.library.enums.Language;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-26T10:40:03-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public Book bookDtoToBook(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookBuilder book = Book.builder();

        if ( bookDto.getLanguage() != null ) {
            book.language( bookDto.getLanguage().name() );
        }
        book.id( bookDto.getId() );
        book.title( bookDto.getTitle() );
        book.editorial( bookDto.getEditorial() );
        book.year( bookDto.getYear() );
        book.pages( bookDto.getPages() );
        book.format( bookDto.getFormat() );
        book.isbn( bookDto.getIsbn() );

        return book.build();
    }

    @Override
    public BookDto bookToBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDtoBuilder bookDto = BookDto.builder();

        bookDto.authorId( bookAuthorId( book ) );
        bookDto.id( book.getId() );
        bookDto.title( book.getTitle() );
        bookDto.editorial( book.getEditorial() );
        bookDto.year( book.getYear() );
        bookDto.pages( book.getPages() );
        if ( book.getLanguage() != null ) {
            bookDto.language( Enum.valueOf( Language.class, book.getLanguage() ) );
        }
        bookDto.format( book.getFormat() );
        bookDto.isbn( book.getIsbn() );

        return bookDto.build();
    }

    @Override
    public BookViewDto bookToBookViewDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookViewDtoBuilder bookViewDto = BookViewDto.builder();

        bookViewDto.authorName( bookAuthorName( book ) );
        bookViewDto.language( book.getLanguage() );
        bookViewDto.id( book.getId() );
        bookViewDto.title( book.getTitle() );
        bookViewDto.editorial( book.getEditorial() );
        bookViewDto.year( book.getYear() );
        bookViewDto.pages( book.getPages() );
        bookViewDto.format( book.getFormat() );

        return bookViewDto.build();
    }

    private Integer bookAuthorId(Book book) {
        if ( book == null ) {
            return null;
        }
        Author author = book.getAuthor();
        if ( author == null ) {
            return null;
        }
        Integer id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String bookAuthorName(Book book) {
        if ( book == null ) {
            return null;
        }
        Author author = book.getAuthor();
        if ( author == null ) {
            return null;
        }
        String name = author.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
