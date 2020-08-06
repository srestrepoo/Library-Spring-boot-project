package com.training.library;

import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;

import java.util.List;

public interface IRegisterService {

    List<RegisterViewDto> saveRegisters(List<RegisterDto> registerDtoList);

}
