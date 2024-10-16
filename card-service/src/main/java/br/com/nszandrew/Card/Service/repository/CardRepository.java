package br.com.nszandrew.Card.Service.repository;

import br.com.nszandrew.Card.Service.model.Card;
import br.com.nszandrew.Card.Service.model.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByAccountNumberAndCardType(String accountNumber, CardType cardType);
    Card findByAccountNumber(String accountNumber);
}
