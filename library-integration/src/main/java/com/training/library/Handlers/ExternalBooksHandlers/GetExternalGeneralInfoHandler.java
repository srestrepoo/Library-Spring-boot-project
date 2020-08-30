package com.training.library.Handlers.ExternalBooksHandlers;

import com.training.library.IExternalLibraryService;
import com.training.library.dtos.ExternalLibrary.ExternalGeneralInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class GetExternalGeneralInfoHandler implements GenericHandler<String> {

    @Autowired
    private IExternalLibraryService externalLibraryService;

    @Override
    public Message<ExternalGeneralInfoDto[]> handle(String o, MessageHeaders messageHeaders) {

        ExternalGeneralInfoDto[] generalInfoList = externalLibraryService
                .getExternalGeneralInfo(messageHeaders.get("externalToken").toString());

        return MessageBuilder
                .withPayload(generalInfoList)
                .copyHeaders(messageHeaders)
                .build();
    }

}
