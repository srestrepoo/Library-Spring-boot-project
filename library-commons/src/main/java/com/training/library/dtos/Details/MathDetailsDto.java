package com.training.library.dtos.Details;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MathDetailsDto implements DetailsDto {

    private Integer id;
    @NonNull
    private String subcategory;
    @NonNull
    private String exercise;
    @NonNull
    private String answer;

}
