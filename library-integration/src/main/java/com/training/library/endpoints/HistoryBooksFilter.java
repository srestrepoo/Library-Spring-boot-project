package com.training.library.endpoints;

import com.training.library.dtos.Book.BookDto;
import com.training.library.enums.CurrencyEnum;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class HistoryBooksFilter implements MessageSelector {

    @Override
    public boolean accept(Message<?> message) {
        BookDto bookDto = (BookDto) message.getPayload();
        if(bookDto.getCurrency().equals(CurrencyEnum.USD)){
            return bookDto.getPrice() <= 45;
        } else{
            return differentCurrency(bookDto.getPrice(), bookDto.getCurrency());
        }
    }

    private Boolean differentCurrency(Double price, CurrencyEnum currency){
        if(currency.equals(CurrencyEnum.COP)){
            return price <= 135000;
        } else if(currency.equals(CurrencyEnum.EUR)){
            return price <= 39;
        } else {
            return false;
        }
    }
}
