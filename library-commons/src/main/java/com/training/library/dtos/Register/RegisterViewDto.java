package com.training.library.dtos.Register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterViewDto {

    private Integer id;
    private Integer bookId;
    private Integer bookOrderId;

}
