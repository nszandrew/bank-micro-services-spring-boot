package br.com.nszandrew.service;

import br.com.nszandrew.model.dto.TransactionRequestDTO;
import br.com.nszandrew.model.dto.TransactionResponseDTO;

import java.math.BigDecimal;

public interface TransactionService {

    TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO);
    TransactionResponseDTO depositBalanceAccount(BigDecimal balance, String idCustomer);
    TransactionResponseDTO withdrawBalanceAccount(BigDecimal balance, String idCustomer);
}
