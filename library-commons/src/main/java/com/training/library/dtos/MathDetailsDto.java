package com.training.library.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MathDetailsDto implements Details{

    @NonNull
    private String subcategory;
    @NonNull
    private String exercise;
    @NonNull
    private String answer;

}
