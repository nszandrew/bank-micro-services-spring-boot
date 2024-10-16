package br.com.nszandrew.client.account;

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
