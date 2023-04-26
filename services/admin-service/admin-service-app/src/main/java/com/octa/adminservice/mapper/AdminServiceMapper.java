package com.octa.adminservice.mapper;

import com.octa.adminservice.model.Card;
import http.request.CreateCardRequest;
import http.response.CreateCardResponse;
import http.response.GetCardResponse;
import http.response.UpdateCardResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminServiceMapper {

    Card fromCreateCardRequestToCard(CreateCardRequest createCardRequest);

    CreateCardResponse fromCardToCreateCardResponse(Card card);

    @AfterMapping
    default void setValuefromCardRequestToCard(CreateCardRequest createCardRequest,
                                               @MappingTarget Card card){
        card.setCardUuid(UUID.randomUUID().toString());
    }

    GetCardResponse fromCardToGetCardResponse(Card card);

    UpdateCardResponse froCardToUpdateCardResponse(Card updatedCard);

    List<GetCardResponse> fromListOfCardToListOfGetCardResponse(List<Card> listOfCard);
}
