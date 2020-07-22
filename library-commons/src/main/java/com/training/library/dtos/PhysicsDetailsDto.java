package com.training.library.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicsDetailsDto implements DetailsDto {

    private Integer id;
    @NonNull
    private String subcategory;
    @NonNull
    private String exercise;
    @NonNull
    private String answer;

}
