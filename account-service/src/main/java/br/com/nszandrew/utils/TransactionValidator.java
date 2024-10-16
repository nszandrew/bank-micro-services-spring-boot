package br.com.nszandrew.utils;

import br.com.nszandrew.exception.custom.AccountNotFoundException;
import br.com.nszandrew.exception.custom.InsufficientFundsException;
import br.com.nszandrew.exception.custom.StatusAccountIsClosed;
import br.com.nszandrew.model.StatusAccount;
import br.com.nszandrew.model.dto.TransferTEDMoneyDTO;
import br.com.nszandrew.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionValidator {

    private final AccountRepository repository;

    @Autowired
    public TransactionValidator(AccountRepository repository) {
        this.repository = repository;
    }

    public void validateTransfer(TransferTEDMoneyDTO transfer) {
        log.info("Validating transfer...");
        var verifySender = repository.findByaccountNumber(transfer.accountNumberSender());
        repository.findByagency(transfer.accountNumberSender());
        if (verifySender.getAccountNumber() == null) {throw new AccountNotFoundException("Sender account not found");}
        if (verifySender.getStatusAccount() == StatusAccount.CLOSED) {throw new StatusAccountIsClosed("Sender account is closed");}
        if (verifySender.getBalance().compareTo(transfer.amount()) < 0) {throw new InsufficientFundsException("Insufficient funds for this transfer");}


        var verifyReceiver = repository.findByaccountNumber(transfer.accountNumberReceive());
        repository.findByagency(transfer.accountNumberReceive());
        if (verifyReceiver == null) {throw new AccountNotFoundException("Receiver account not found");}
        if(verifyReceiver.getStatusAccount() == StatusAccount.CLOSED) {throw new StatusAccountIsClosed("Receiver account is closed");}
    }
}
