package com.training.library;

import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;
import com.training.library.entities.Register;
import com.training.library.mappers.RegisterMapper;
import com.training.library.repositories.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImp implements IRegisterService{

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private RegisterMapper registerMapper;

    @Override
    @Transactional
    public List<RegisterViewDto> saveRegisters(List<RegisterDto> registerDtoList) {
        List<Register> registerList = registerDtoList.stream()
                .map(registerDto -> registerMapper.dtoToRegister(registerDto))
                .collect(Collectors.toList());

        List<RegisterViewDto> registerViewDtoList = registerRepository.saveAll(registerList).stream()
                .map(register -> registerMapper.registerToRegisterViewDto(register))
                .collect(Collectors.toList());

        return registerViewDtoList;
    }
}
