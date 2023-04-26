package com.octa.adminservice.service.impl;

import com.octa.adminservice.mapper.AdminServiceMapper;
import com.octa.adminservice.model.Card;
import com.octa.adminservice.port.persistence.IAdminRepository;
import com.octa.adminservice.service.IAdminService;
import http.request.CreateCardRequest;
import http.request.UpdateCardRequest;
import http.response.CreateCardResponse;
import http.response.GetCardResponse;
import http.response.UpdateCardResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public class AdminServiceImpl implements IAdminService {

    private final AdminServiceMapper adminServiceMapper;

    private final IAdminRepository adminRepository;

    public AdminServiceImpl(AdminServiceMapper adminServiceMapper, IAdminRepository adminRepository) {
        this.adminServiceMapper = adminServiceMapper;
        this.adminRepository = adminRepository;
    }

    @Override
    public CreateCardResponse save(CreateCardRequest createCardRequest) {
        Card card=adminServiceMapper.fromCreateCardRequestToCard(createCardRequest);
        Card savedCard = adminRepository.save(card);
        return adminServiceMapper.fromCardToCreateCardResponse(savedCard);
    }

    @Override
    public GetCardResponse getCardByUuid(String cardUuid) {
        Card card = adminRepository.findByCardUuid(cardUuid).orElseThrow(() -> new RuntimeException("card not found"));
        return adminServiceMapper.fromCardToGetCardResponse(card);
    }

    @Override
    public UpdateCardResponse updateCardByUuid(UpdateCardRequest updateCardRequest) {
        Card card = adminRepository.findByCardUuid(updateCardRequest.getCardUuid()).orElseThrow(() -> new RuntimeException("card not found"));
        card.setTitle(updateCardRequest.getTitle());
        card.setPrice(updateCardRequest.getPrice());
        card.setGender(updateCardRequest.getGender());
        card.setImage(updateCardRequest.getImage());
        Card updatedCard = adminRepository.save(card);
        return adminServiceMapper.froCardToUpdateCardResponse(updatedCard);
    }

    @Override
    public void deleteCardByUuid(String cardUuid) {
       Card card = adminRepository.findByCardUuid(cardUuid).orElseThrow(() -> new RuntimeException("card not found"));
        adminRepository.deleteById(card.getId());
    }

    @Override
    public List<GetCardResponse> getAllCard() {
        List<Card> listOfCard = adminRepository.findAll();
        return adminServiceMapper.fromListOfCardToListOfGetCardResponse(listOfCard);
    }
}
