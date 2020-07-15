package com.training.library.dtos;

import com.training.library.enums.Nationality;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDetailsDto implements Details{

    @NonNull
    private String historicalPeriod;
    @NonNull
    private Nationality country;
    @NonNull
    private String censure;

}
