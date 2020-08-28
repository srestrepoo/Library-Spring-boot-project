package com.training.library;

import com.training.library.dtos.ExternalLibrary.*;
import com.training.library.enums.ExternalPropertyEnum;
import com.training.library.exceptions.errorhandler.RestTemplateResponseErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExternalLibraryServiceImp implements IExternalLibraryService {

    @Autowired
    Environment env;

    private RestTemplate restTemplate;

    @Autowired
    public ExternalLibraryServiceImp(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    @Override
    public ExternalCredentialsDto getCredentials() {
        String loginUrl
                = env.getProperty(ExternalPropertyEnum.BASE_URL.getValue()) + "/login";

        HttpEntity<ExternalLoginDto> request = new HttpEntity<>(
                ExternalLoginDto.builder()
                        .nombreUsuario(env.getProperty(ExternalPropertyEnum.USERNAME.getValue()))
                        .contrasena(env.getProperty(ExternalPropertyEnum.PASSWORD.getValue()))
                        .build());

        return restTemplate.exchange(loginUrl, HttpMethod.POST, request, ExternalCredentialsDto.class).getBody();
    }

    @Override
    public ExternalGeneralInfoDto[] getExternalGeneralInfo(String accessToken) {
        String getGeneralInfoUrl
                = env.getProperty(ExternalPropertyEnum.BASE_URL.getValue()) + "/libros";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);

        return restTemplate.exchange(getGeneralInfoUrl, HttpMethod.GET, entity, ExternalGeneralInfoDto[].class).getBody();
    }

    @Override
    public ExternalAuthorDto getExternalAuthor(String accessToken, ExternalAuthorFilterDto externalAuthorFilterDto) {
        String getGeneralInfoUrl
                = env.getProperty(ExternalPropertyEnum.BASE_URL.getValue()) + "/libros";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<ExternalAuthorFilterDto> entity = new HttpEntity(externalAuthorFilterDto, headers);

        return restTemplate.exchange(getGeneralInfoUrl, HttpMethod.POST, entity, ExternalAuthorDto.class).getBody();
    }

}
