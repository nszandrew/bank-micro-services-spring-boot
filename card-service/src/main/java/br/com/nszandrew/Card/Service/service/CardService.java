package br.com.nszandrew.Card.Service.service;

import br.com.nszandrew.Card.Service.client.PaymentCreditRequestDTO;
import br.com.nszandrew.Card.Service.client.PaymentDebitRequestDTO;
import br.com.nszandrew.Card.Service.model.CardType;
import br.com.nszandrew.Card.Service.model.dto.CardRequestDTO;
import br.com.nszandrew.Card.Service.model.dto.CardResponseDTO;

public interface CardService {

    CardResponseDTO createCard(CardRequestDTO data);
    CardResponseDTO getCard(String accountNumber,CardType type);
    CardResponseDTO blockCard(String accountNumber, CardType type);
    PaymentCreditRequestDTO creditPurchase(PaymentCreditRequestDTO data);
    PaymentDebitRequestDTO debitPurchase(PaymentDebitRequestDTO data);
}
