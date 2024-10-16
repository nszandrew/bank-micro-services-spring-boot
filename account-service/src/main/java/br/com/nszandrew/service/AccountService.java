package br.com.nszandrew.service;

import br.com.nszandrew.model.dto.AccountResponseDTO;
import br.com.nszandrew.model.dto.TransferTEDMoneyDTO;
import br.com.nszandrew.model.dto.TransferTEDMoneyResponseDTO;

import java.math.BigDecimal;

public interface AccountService {

    AccountResponseDTO createAccount(String accountType, Long id);
    AccountResponseDTO depositBalanceAccount(BigDecimal balance, String id);
    AccountResponseDTO withdrawBalanceAccount(BigDecimal balance, String id);
    TransferTEDMoneyResponseDTO transferMoneyByTED(TransferTEDMoneyDTO transfer);
    AccountResponseDTO cardPayment(BigDecimal balance, String accountNumber);
    AccountResponseDTO updateAccountType(String accountRequestDTO, Long id);
    AccountResponseDTO getAccount(Long id);
    AccountResponseDTO getAccountByNumber(String accountNumber);
    String deleteAccount(Long id);
}
