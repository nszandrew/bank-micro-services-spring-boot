package br.com.nszandrew.model.dto;

import java.math.BigDecimal;

public record PaymentDebitRequestDTO(String accountNumber, BigDecimal amount) {
}
