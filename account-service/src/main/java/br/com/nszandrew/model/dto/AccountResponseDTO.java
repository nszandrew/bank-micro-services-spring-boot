package br.com.nszandrew.model.dto;

import br.com.nszandrew.model.AccountType;
import br.com.nszandrew.model.StatusAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO(String accountNumber,
                                 String agency,
                                 Long idCustomer,
                                 AccountType accountType,
                                 StatusAccount statusAccount,
                                 BigDecimal balance,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt){}
