package com.training.library;

import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Author.FilterAuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.FilterBookDto;
import com.training.library.dtos.BookOrder.BookOrderDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.dtos.ExternalLibrary.ExternalAuthorFilterDto;
import com.training.library.dtos.ExternalLibrary.ExternalCredentialsDto;
import com.training.library.dtos.ExternalLibrary.ExternalGeneralInfoDto;
import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;
import com.training.library.enums.CurrencyEnum;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.StateEnum;
import com.training.library.mappers.ExternalLibraryMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LibraryApplication.class)
public class createOrderIT {

    @MockBean
    private IBookService bookServiceMock;

    @MockBean
    private IBookOrderService bookOrderServiceMock;

    @MockBean
    private IRegisterService registerServiceMock;

    @Autowired
    private OrderGateway orderGateway;

    public static BookDto mathTestBook;

    @BeforeAll
    public static void setupData() {
        MathDetailsDto mathDetailsDto = MathDetailsDto.builder().id(1).subcategory("Test subcategory")
                .exercise("Test Exercise").answer("Test answer").build();

        mathTestBook = BookDto.builder()
                .id(1).authorId(1).title("Test title").active(true).state(StateEnum.ACCEPTABLE)
                .language(LanguageEnum.FRENCH).currency(CurrencyEnum.USD).editorial("Edit Test")
                .format("Display").pages(100).year(1990).price(20.0).isbn("123")
                .detailsDto(mathDetailsDto)
                .build();
    }

    @Test
    public void createOrderFlow() {

        //Get Books
        FilterBookDto filterBookDto = FilterBookDto.builder().build();
        List<BookDto> bookInputMockedList = Arrays.asList(mathTestBook);
        Mockito.when(bookServiceMock.getAllBooks(filterBookDto))
                .thenReturn(bookInputMockedList);

        //Math process
        List<BookDto> mathBooksToSave = Arrays.asList(mathTestBook.toBuilder().id(null).state(StateEnum.EXCELLENT).build());
        List<BookDto> mathBooksSaved = Arrays.asList(mathTestBook.toBuilder().id(2).state(StateEnum.EXCELLENT).build());
        Mockito.when(bookServiceMock.createBookCopies(mathTestBook.getAuthorId(), mathTestBook.getDetailsDto(), mathBooksToSave))
                .thenReturn(mathBooksSaved);

        //Aggregator Process
        List<BookDto> newBooksCreated = Arrays.asList(mathTestBook.toBuilder().id(2).state(StateEnum.EXCELLENT).build());

        //Create Order
        BookOrderDto bookOrderDto = BookOrderDto.builder().id(1).build();
        Mockito.when(bookOrderServiceMock.createBookOrder(new BookOrderDto()))
                .thenReturn(bookOrderDto);

        //Add Registers
        List<RegisterDto> registerDtoList = newBooksCreated.stream().map(
                newBookDto -> RegisterDto.builder().bookDto(newBookDto).bookOrderDto(bookOrderDto).build()
        ).collect(Collectors.toList());
        List<RegisterViewDto> registerViewDtoList = registerDtoList.stream().map(
                registerDto -> RegisterViewDto.builder()
                        .id((int) (Math.random() * 100))
                        .bookOrderId(registerDto.getBookOrderDto().getId())
                        .bookId(registerDto.getBookDto().getId())
                        .build()
        ).collect(Collectors.toList());
        Mockito.when(registerServiceMock.saveRegisters(registerDtoList))
                .thenReturn(registerViewDtoList);

        List<RegisterViewDto> integrationResult = orderGateway.createOrder(filterBookDto);

        Mockito.verify(bookServiceMock, Mockito.times(1)).updateStateAndActiveById(1, StateEnum.BAD);
        Assertions.assertEquals(integrationResult.size(), 1);
        Assertions.assertEquals(integrationResult.get(0).getBookId(), 2);
        Assertions.assertEquals(integrationResult.get(0).getBookOrderId(), 1);

    }
}
