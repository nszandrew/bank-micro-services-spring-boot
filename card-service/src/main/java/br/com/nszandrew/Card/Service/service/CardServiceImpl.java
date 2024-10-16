package br.com.nszandrew.Card.Service.service;

import br.com.nszandrew.Card.Service.client.PaymentCreditRequestDTO;
import br.com.nszandrew.Card.Service.client.PaymentDebitRequestDTO;
import br.com.nszandrew.Card.Service.client.PaymentService;
import br.com.nszandrew.Card.Service.model.Card;
import br.com.nszandrew.Card.Service.model.CardType;
import br.com.nszandrew.Card.Service.model.Status;
import br.com.nszandrew.Card.Service.model.dto.CardRequestDTO;
import br.com.nszandrew.Card.Service.model.dto.CardResponseDTO;
import br.com.nszandrew.Card.Service.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final PaymentService service;

    @Override
    @Transactional
    public CardResponseDTO createCard(CardRequestDTO data) {
        log.info("Create Card");
        Card card = new Card(data);
        repository.save(card);
        return new CardResponseDTO(card);
    }

    @Override
    public CardResponseDTO getCard(String accountNumber, CardType type) {
        log.info("Get Card");
        var card = repository.findByAccountNumberAndCardType(accountNumber, type);
        if(card.getStatus() == Status.BLOCK) {throw new RuntimeException("Card is blocked");}

        return new CardResponseDTO(card);
    }

    @Override
    @Transactional
    public CardResponseDTO blockCard(String accountNumber, CardType type) {
        log.info("Block Card");
        var card = repository.findByAccountNumberAndCardType(accountNumber, type);
        card.setStatus(Status.BLOCK);
        card.setUpdatedAt(LocalDate.now());
        repository.save(card);
        return null;
    }

    @Override
    @Transactional
    public PaymentCreditRequestDTO creditPurchase(PaymentCreditRequestDTO data) {
        var creditPurchase = repository.findByAccountNumber(data.accountNumber());

        if(creditPurchase.getStatus() == Status.BLOCK) {throw new RuntimeException("Card is blocked");}
        if(creditPurchase.getCreditLimit().compareTo(data.amount()) < 0){throw new RuntimeException("Card limit exceeded");}

        service.creditPurchase(new PaymentCreditRequestDTO(data.accountNumber(), data.amount(), data.installments()));
        creditPurchase.setCreditLimit(creditPurchase.getCreditLimit().subtract(data.amount()));
        repository.save(creditPurchase);

        return data;
    }

    @Override
    public PaymentDebitRequestDTO debitPurchase(PaymentDebitRequestDTO data) {
        var creditPurchase = repository.findByAccountNumber(data.accountNumber());
        if(creditPurchase.getStatus() == Status.BLOCK) {throw new RuntimeException("Card is blocked");}

        service.debitPurchase(new PaymentDebitRequestDTO(data.accountNumber(), data.amount()));

        return data;
    }

}
