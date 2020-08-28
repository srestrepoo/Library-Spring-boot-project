package com.training.library;

import com.training.library.dtos.ExternalLibrary.ExternalAuthorDto;
import com.training.library.dtos.ExternalLibrary.ExternalAuthorFilterDto;
import com.training.library.dtos.ExternalLibrary.ExternalCredentialsDto;
import com.training.library.dtos.ExternalLibrary.ExternalGeneralInfoDto;

public interface IExternalLibraryService {

    ExternalCredentialsDto getCredentials();

    ExternalGeneralInfoDto[] getExternalGeneralInfo(String accessToken);

    ExternalAuthorDto getExternalAuthor(String accessToken, ExternalAuthorFilterDto externalAuthorFilterDto);

}
