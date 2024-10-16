package br.com.nszandrew.Card.Service.controller;

import br.com.nszandrew.Card.Service.client.PaymentCreditRequestDTO;
import br.com.nszandrew.Card.Service.client.PaymentDebitRequestDTO;
import br.com.nszandrew.Card.Service.model.CardType;
import br.com.nszandrew.Card.Service.model.dto.CardRequestDTO;
import br.com.nszandrew.Card.Service.model.dto.CardResponseDTO;
import br.com.nszandrew.Card.Service.service.CardService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @PostMapping
    @Hidden
    public ResponseEntity<CardResponseDTO> addCard(@RequestBody @Valid CardRequestDTO data) {
        var newCard = service.createCard(data);
        return new ResponseEntity<>(newCard, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CardResponseDTO> getAllCards(@RequestParam String accountNumber, @RequestParam CardType type) {
        var getCard = service.getCard(accountNumber, type);
        return new ResponseEntity<>(getCard, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CardResponseDTO> blockCard(@RequestParam String accountNumber, @RequestParam CardType type) {
        service.blockCard(accountNumber, type);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/purchasecredit")
    public ResponseEntity<PaymentCreditRequestDTO> creditPurchase(@RequestBody @Valid PaymentCreditRequestDTO data){
        var creditPurchase =  service.creditPurchase(data);
        return new ResponseEntity<>(creditPurchase, HttpStatus.OK);
    }

    @GetMapping("/purchasedebit")
    public ResponseEntity<PaymentDebitRequestDTO> debitPurchase(@RequestBody @Valid PaymentDebitRequestDTO data){
        var debitPurchase = service.debitPurchase(data);
        return new ResponseEntity<>(debitPurchase, HttpStatus.OK);
    }
}
