package com.training.library;

import com.training.library.Handlers.*;
;
import com.training.library.Handlers.CreateOrderHandlers.CreateHistoryOrderHandler;
import com.training.library.Handlers.CreateOrderHandlers.CreateMathOrderHandler;
import com.training.library.Handlers.CreateOrderHandlers.CreateOrderProxyHandler;
import com.training.library.Handlers.CreateOrderHandlers.CreatePhysicsOrderHandler;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.HistoryDetailsDto;
import com.training.library.dtos.Details.MathDetailsDto;
import com.training.library.dtos.Details.PhysicsDetailsDto;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.SubscribableChannel;

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
    private DiscardFilteredHandler discardFilteredHandler;

    @Bean(name = "inputChannel")
    public SubscribableChannel inputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean(name = "aggregateChannel")
    public SubscribableChannel aggregateChannel() {
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
                .from(inputChannel())
                .handle(getBooksHandler)
                .split()
                .channel(e -> e.executor(this.executor()))
                .route(BookDto.class, this::getBookCategory,
                        mapping -> mapping
                                .subFlowMapping(PhysicsDetailsDto.class, subFlow -> subFlow
                                        .<BookDto>filter(bookDto -> bookDto.getState().equals(StateEnum.ACCEPTABLE) ||
                                                        bookDto.getState().equals(StateEnum.BAD),
                                                filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                                                        .handle(discardFilteredHandler)
                                                        .channel(aggregateChannel())
                                                ))
                                )
                                .subFlowMapping(MathDetailsDto.class, subFlow -> subFlow
                                        .<BookDto>filter(bookDto -> bookDto.getState().equals(StateEnum.ACCEPTABLE) ||
                                                        bookDto.getState().equals(StateEnum.BAD),
                                                filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                                                        .handle(discardFilteredHandler)
                                                        .channel(aggregateChannel())
                                                ))
                                )
                                .subFlowMapping(HistoryDetailsDto.class, subFlow -> subFlow
                                        .filter(historyBooksFilter,
                                                filterEndpointSpec -> filterEndpointSpec.discardFlow(discFlow -> discFlow
                                                        .handle(discardFilteredHandler)
                                                        .channel(aggregateChannel())
                                                ))
                                )

                )
                .handle(updateBookStateHandler)
                .handle(createOrderProxyHandler)
                .channel(aggregateChannel())
                .aggregate(a -> a.processor(getBooksOrderAggregator()))
                .get();
    }

    private Class getBookCategory(BookDto bookDto) {
        return bookDto.getDetailsDto().getClass();
    }
}
