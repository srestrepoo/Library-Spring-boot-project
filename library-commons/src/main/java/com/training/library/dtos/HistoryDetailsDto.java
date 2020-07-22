package com.training.library.dtos;

import com.training.library.enums.Nationality;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDetailsDto implements DetailsDto {

    private Integer id;
    @NonNull
    private String historicalPeriod;
    @NonNull
    private Nationality country;
    @NonNull
    private String censure;

}
