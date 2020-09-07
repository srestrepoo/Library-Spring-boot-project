package com.training.library;

import com.training.library.dtos.ExternalLibrary.*;

public interface IExternalLibraryService {

    ExternalCredentialsDto getCredentials(ExternalLoginDto externalLoginDto);

    ExternalGeneralInfoDto[] getExternalGeneralInfo(String accessToken);

    ExternalAuthorDto getExternalAuthor(String accessToken, ExternalAuthorFilterDto externalAuthorFilterDto);

}
