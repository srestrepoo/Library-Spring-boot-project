package com.training.library;

import com.training.library.Handlers.*;
;
import com.training.library.Handlers.CreateOrderHandlers.*;
import com.training.library.channels.OrderChannel;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.HistoryDetailsDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.dtos.Register.RegisterViewDto;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.SubscribableChannel;
import org.apache.commons.collections4.CollectionUtils;


import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
public class OrderConfiguration {

    @Autowired
    private GetBooksHandler getBooksHandler;

    @Autowired
    private CreateOrderProxyHandler createOrderProxyHandler;

    @Autowired
    private UpdateBookStateHandler updateBookStateHandler;

    @Autowired
    private HistoryBooksFilter historyBooksFilter;

    @Autowired
    private AddRegistersHandler addRegistersHandler;

    @Bean(name = OrderChannel.inputOrderChannel)
    public SubscribableChannel inputOrderChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = OrderChannel.aggregateOrderChannel)
    public SubscribableChannel aggregateOrderChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public Executor executor() {
        return Executors.newFixedThreadPool(2);
    }

    @Bean
    public BooksOrderAggregator getBooksOrderAggregator() {
        return new BooksOrderAggregator();
    }

    @Bean
    public IntegrationFlow getBooksFlow() {
        return IntegrationFlows
                .from(inputOrderChannel())
                .handle(getBooksHandler)
                .filter(CollectionUtils::isNotEmpty, filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                        .handle((payload, messageHeaders) -> Collections.<RegisterViewDto>emptyList())
                ))
                .split()
                .channel(e -> e.executor(this.executor()))
                .<BookDto, Class>route(this::getBookCategory,
                        mapping -> mapping
                                .resolutionRequired(false)
                                .subFlowMapping(MathDetailsDto.class, subFlow -> subFlow
                                        .<BookDto>filter(bookDto -> bookDto.getState().equals(StateEnum.ACCEPTABLE) ||
                                                        bookDto.getState().equals(StateEnum.BAD),
                                                filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                                                        .handle((payload, messageHeaders) -> Collections.<BookDto>emptyList())
                                                        .channel(aggregateOrderChannel())
                                                ))
                                )
                                .subFlowMapping(HistoryDetailsDto.class, subFlow -> subFlow
                                        .filter(historyBooksFilter,
                                                filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                                                        .handle((payload, messageHeaders) -> Collections.<BookDto>emptyList())
                                                        .channel(aggregateOrderChannel())
                                                ))
                                )
                                .defaultSubFlowMapping(subFlow -> subFlow
                                       .transform(bookDto -> Arrays.asList(bookDto))
                                )
                )
                .handle(updateBookStateHandler)
                .handle(createOrderProxyHandler)
                .channel(aggregateOrderChannel())
                .aggregate(a -> a.processor(getBooksOrderAggregator()))
                .filter(CollectionUtils::isNotEmpty, filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                        .handle((payload, messageHeaders) -> Collections.<RegisterViewDto>emptyList())
                ))
                .handle(addRegistersHandler)
                .get();
    }

    private Class getBookCategory(BookDto bookDto) {
        return bookDto.getDetailsDto().getClass();
    }
}
