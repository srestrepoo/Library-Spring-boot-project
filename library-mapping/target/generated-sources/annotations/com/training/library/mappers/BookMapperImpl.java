package com.training.library.mappers;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookDto.BookDtoBuilder;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.BookViewDto.BookViewDtoBuilder;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import com.training.library.entities.Book.BookBuilder;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-05T14:36:09-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public Book bookDtoToBook(BookDto bookDto, Author newAuthor) {
        if ( bookDto == null && newAuthor == null ) {
            return null;
        }

        BookBuilder book = Book.builder();

        if ( bookDto != null ) {
            book.id( bookDto.getId() );
            book.title( bookDto.getTitle() );
            book.editorial( bookDto.getEditorial() );
            book.year( bookDto.getYear() );
            book.pages( bookDto.getPages() );
            book.language( bookDto.getLanguage() );
            book.format( bookDto.getFormat() );
            book.isbn( bookDto.getIsbn() );
        }
        if ( newAuthor != null ) {
            book.author( newAuthor );
        }

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
        bookDto.language( book.getLanguage() );
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
        bookViewDto.id( book.getId() );
        bookViewDto.title( book.getTitle() );
        bookViewDto.editorial( book.getEditorial() );
        bookViewDto.year( book.getYear() );
        bookViewDto.pages( book.getPages() );
        bookViewDto.language( book.getLanguage() );
        bookViewDto.format( book.getFormat() );

        return bookViewDto.build();
    }

    @Override
    public void updateBookFromDto(BookDto bookDto, Book book, Author newAuthor) {
        if ( bookDto == null && newAuthor == null ) {
            return;
        }

        if ( bookDto != null ) {
            book.setTitle( bookDto.getTitle() );
            book.setEditorial( bookDto.getEditorial() );
            book.setYear( bookDto.getYear() );
            book.setPages( bookDto.getPages() );
            book.setLanguage( bookDto.getLanguage() );
            book.setFormat( bookDto.getFormat() );
            book.setIsbn( bookDto.getIsbn() );
        }
        if ( newAuthor != null ) {
            book.setAuthor( newAuthor );
        }
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
