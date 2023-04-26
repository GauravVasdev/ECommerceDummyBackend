package com.octa.adminservice.http.controller;

import com.octa.adminservice.service.IAdminService;
import constant.AdminServiceConstant;
import http.request.CreateCardRequest;
import http.request.UpdateCardRequest;
import http.response.CreateCardResponse;
import http.response.GetCardResponse;
import http.response.UpdateCardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = AdminServiceConstant.BASE_URL)
public class AdminApiController {

    private final IAdminService adminService;


    public AdminApiController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(AdminServiceConstant.CREATE_CARD_URL)
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest createCardRequest){
        CreateCardResponse createCardResponse = adminService.save(createCardRequest);
        return new ResponseEntity<>(createCardResponse, HttpStatus.CREATED);
    }

    @GetMapping(AdminServiceConstant.GET_CARD_URL)
    public ResponseEntity<GetCardResponse> getCardByUuid(@PathVariable("cardUuid") String cardUuid){
        GetCardResponse getCardResponse = adminService.getCardByUuid(cardUuid);
        return new ResponseEntity<>(getCardResponse, HttpStatus.OK);
    }

    @PutMapping(AdminServiceConstant.UPDATE_CARD_URL)
    public ResponseEntity<UpdateCardResponse> updateCardByUuid(@RequestBody UpdateCardRequest updateCardRequest){
        UpdateCardResponse updateCardResponse = adminService.updateCardByUuid(updateCardRequest);
        return new ResponseEntity<>(updateCardResponse, HttpStatus.OK);
    }

    @DeleteMapping(AdminServiceConstant.DELETE_CARD_URL)
    public ResponseEntity<String> deleteCardByUuid(@PathVariable("cardUuid") String cardUuid){
        adminService.deleteCardByUuid(cardUuid);
        return new ResponseEntity<>("card deleted", HttpStatus.OK);
    }

    @GetMapping(AdminServiceConstant.GET_ALL_CARD_URL)
    public ResponseEntity<List<GetCardResponse>> getAllCard(){
        List<GetCardResponse> getCardResponse = adminService.getAllCard();
        return new ResponseEntity<>(getCardResponse, HttpStatus.OK);
    }
}
