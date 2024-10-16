package br.com.nszandrew.model.dto;

import java.math.BigDecimal;

public record PaymentCreditRequestDTO(String accountNumber, BigDecimal amount, Integer installments) {
}
