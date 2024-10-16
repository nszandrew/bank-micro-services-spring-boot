package br.com.nszandrew.Card.Service.client;

import java.math.BigDecimal;

public record PaymentCreditRequestDTO(String accountNumber, BigDecimal amount, Integer installments) {
}
