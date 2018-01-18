package com.example.asanre.searchmoviedb.domain.useCase.base;

public interface BaseUseCase<RESPONSE_DATA, REQUEST_DATA> {

    RESPONSE_DATA execute(REQUEST_DATA params);
}
