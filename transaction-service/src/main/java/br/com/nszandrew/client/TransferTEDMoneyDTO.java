package br.com.nszandrew.client;

import java.math.BigDecimal;

public record TransferTEDMoneyDTO(BigDecimal amount, String accountNumberReceive, String agencyReceive, String accountNumberSender, String agencySender) {
}
