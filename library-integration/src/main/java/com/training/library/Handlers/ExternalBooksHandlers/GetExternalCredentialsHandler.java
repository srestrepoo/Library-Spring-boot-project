package com.training.library.Handlers.ExternalBooksHandlers;

import com.training.library.IExternalLibraryService;
import com.training.library.dtos.ExternalLibrary.ExternalCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetExternalCredentialsHandler implements GenericHandler<String> {

    @Autowired
    private IExternalLibraryService externalLibraryService;

    @Override
    public Message handle(String o, MessageHeaders messageHeaders) {
        return MessageBuilder
                .withPayload(o)
                .setHeader("externalToken",externalLibraryService.getCredentials().getToken())
                .build();
    }

}
