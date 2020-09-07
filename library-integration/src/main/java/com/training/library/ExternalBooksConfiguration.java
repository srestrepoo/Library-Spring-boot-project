package com.training.library;

import com.training.library.Handlers.ExternalBooksHandlers.*;
import com.training.library.channels.ExternalBookChannel;
import com.training.library.dtos.ExternalLibrary.ExternalLoginDto;
import com.training.library.endpoints.BooksAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.HeaderEnricherSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class ExternalBooksConfiguration {

    @Autowired
    private GetExternalGeneralInfoHandler getExternalGeneralInfoHandler;

    @Autowired
    private PersistExternalAuthorHandler persistExternalAuthorHandler;

    @Autowired
    private ExternalGeneralInfoHandler externalGeneralInfoHandler;

    @Autowired
    private DeleteObsoleteExternalBooksHandler deleteObsoleteExternalBooksHandler;

    @Autowired
    private IExternalLibraryService externalLibraryService;

    @Bean(name = ExternalBookChannel.inputExternalBookChannel)
    public SubscribableChannel inputExternalBookChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public BooksAggregator getBooksAggregator() {
        return new BooksAggregator();
    }

    @Bean
    public IntegrationFlow externalBooksFlow() {
        return IntegrationFlows
                .from(inputExternalBookChannel())
                .enrichHeaders(this::getExternalTokenHeaderEnricherSpec)
                .handle(getExternalGeneralInfoHandler)
                .split()
                .handle(persistExternalAuthorHandler)
                .handle(externalGeneralInfoHandler)
                .aggregate(a -> a.processor(getBooksAggregator()))
                .handle(deleteObsoleteExternalBooksHandler)
                .get();
    }

    private HeaderEnricherSpec getExternalTokenHeaderEnricherSpec(HeaderEnricherSpec h) {
        return h.headerFunction("externalToken",
                message -> externalLibraryService.getCredentials((ExternalLoginDto) message.getPayload()).getToken());
    }

}
