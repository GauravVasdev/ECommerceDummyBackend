package com.octa.adminservice.service;

import http.request.CreateCardRequest;
import http.request.UpdateCardRequest;
import http.response.CreateCardResponse;
import http.response.GetCardResponse;
import http.response.UpdateCardResponse;

import java.util.List;

public interface IAdminService {


    CreateCardResponse save(CreateCardRequest createCardRequest);

    GetCardResponse getCardByUuid(String cardUuid);

    UpdateCardResponse updateCardByUuid(UpdateCardRequest updateCardRequest);

    void deleteCardByUuid(String cardUuid);

    List<GetCardResponse> getAllCard();
}
