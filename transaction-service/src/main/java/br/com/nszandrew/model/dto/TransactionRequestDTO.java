package br.com.nszandrew.model.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequestDTO (
        @NotNull
        BigDecimal amount,
        @NotNull
        String accountNumberReceive,
        @NotNull
        String agencyReceive,
        @NotNull
        String accountNumberSender,
        @NotNull
        String agencySender) {
}
