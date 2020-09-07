package com.training.library;

import com.training.library.channels.ExternalBookChannel;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.ExternalLibrary.ExternalLoginDto;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.List;

@MessagingGateway(name="ExternalBooksGateway")
public interface ExternalBooksGateway {

    @Gateway(requestChannel = ExternalBookChannel.inputExternalBookChannel)
    List<BookDto> addExternalBooks(ExternalLoginDto externalLoginDto);

}
