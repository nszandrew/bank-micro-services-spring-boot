package br.com.nszandrew.Card.Service.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardRequestDTO(String accountNumber, LocalDate dueDate, BigDecimal creditLimit) {
}
