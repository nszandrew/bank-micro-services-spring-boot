package br.com.nszandrew.client.card;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardRequestDTO(String accountNumber, LocalDate dueDate, BigDecimal creditLimit) {
}
