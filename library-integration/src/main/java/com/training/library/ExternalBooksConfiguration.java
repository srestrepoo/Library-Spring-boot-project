package com.training.library;

import com.training.library.Handlers.ExternalBooksHandlers.AddExternalBooksHandler;
import com.training.library.Handlers.ExternalBooksHandlers.GetExternalCredentialsHandler;
import com.training.library.Handlers.ExternalBooksHandlers.GetExternalGeneralInfoHandler;
import com.training.library.Handlers.ExternalBooksHandlers.PersistExternalAuthorHandler;
import com.training.library.channels.ExternalBookChannel;
import com.training.library.endpoints.BooksAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.SubscribableChannel;

@Configuration
public class ExternalBooksConfiguration {

    @Autowired
    private GetExternalCredentialsHandler getExternalCredentialsHandler;

    @Autowired
    private GetExternalGeneralInfoHandler getExternalGeneralInfoHandler;

    @Autowired
    private PersistExternalAuthorHandler persistExternalAuthorHandler;

    @Autowired
    private AddExternalBooksHandler addExternalBooksHandler;

    @Bean(name = ExternalBookChannel.inputExternalBookChannel)
    public SubscribableChannel inputExternalBookChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = ExternalBookChannel.aggregateExternalBookChannel)
    public SubscribableChannel aggregateExternalBookChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public BooksAggregator getBooksAggregator() {
        return new BooksAggregator();
    }

    @Bean
    public IntegrationFlow addExternalBooks() {
        return IntegrationFlows
                .from(inputExternalBookChannel())
                .handle(getExternalCredentialsHandler)
                .handle(getExternalGeneralInfoHandler)
                .split()
                .handle(persistExternalAuthorHandler)
                .handle(addExternalBooksHandler)
                .aggregate(a -> a.processor(getBooksAggregator()))
                .get();
    }
}
