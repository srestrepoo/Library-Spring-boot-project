package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorDto.AuthorDtoBuilder;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookDto.BookDtoBuilder;
import com.training.library.entities.Author;
import com.training.library.entities.Author.AuthorBuilder;
import com.training.library.entities.Book;
import com.training.library.entities.Book.BookBuilder;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-15T23:58:25-0500",
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
        author.books( bookDtoSetToBookSet( authorDto.getBooks() ) );

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
        authorDto.books( bookSetToBookDtoSet( author.getBooks() ) );

        return authorDto.build();
    }

    protected Book bookDtoToBook(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookBuilder book = Book.builder();

        book.id( bookDto.getId() );
        book.author( authorDtoToAuthor( bookDto.getAuthor() ) );
        book.title( bookDto.getTitle() );
        book.editorial( bookDto.getEditorial() );
        book.year( bookDto.getYear() );
        book.pages( bookDto.getPages() );
        book.language( bookDto.getLanguage() );
        book.format( bookDto.getFormat() );

        return book.build();
    }

    protected Set<Book> bookDtoSetToBookSet(Set<BookDto> set) {
        if ( set == null ) {
            return null;
        }

        Set<Book> set1 = new HashSet<Book>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BookDto bookDto : set ) {
            set1.add( bookDtoToBook( bookDto ) );
        }

        return set1;
    }

    protected BookDto bookToBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDtoBuilder bookDto = BookDto.builder();

        bookDto.id( book.getId() );
        bookDto.author( authorToAuthorDto( book.getAuthor() ) );
        bookDto.title( book.getTitle() );
        bookDto.editorial( book.getEditorial() );
        bookDto.year( book.getYear() );
        bookDto.pages( book.getPages() );
        bookDto.language( book.getLanguage() );
        bookDto.format( book.getFormat() );

        return bookDto.build();
    }

    protected Set<BookDto> bookSetToBookDtoSet(Set<Book> set) {
        if ( set == null ) {
            return null;
        }

        Set<BookDto> set1 = new HashSet<BookDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Book book : set ) {
            set1.add( bookToBookDto( book ) );
        }

        return set1;
    }
}
