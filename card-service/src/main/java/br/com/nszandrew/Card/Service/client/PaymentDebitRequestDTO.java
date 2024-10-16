package br.com.nszandrew.Card.Service.client;

import java.math.BigDecimal;

public record PaymentDebitRequestDTO(String accountNumber, BigDecimal amount) {
}
