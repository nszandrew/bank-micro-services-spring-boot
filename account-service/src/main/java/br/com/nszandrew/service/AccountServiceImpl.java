package br.com.nszandrew.service;

import br.com.nszandrew.exception.custom.InsufficientFundsException;
import br.com.nszandrew.exception.custom.StatusAccountIsClosed;
import br.com.nszandrew.model.Account;
import br.com.nszandrew.model.AccountType;
import br.com.nszandrew.model.StatusAccount;
import br.com.nszandrew.model.dto.AccountResponseDTO;
import br.com.nszandrew.model.dto.TransferTEDMoneyDTO;
import br.com.nszandrew.model.dto.TransferTEDMoneyResponseDTO;
import br.com.nszandrew.repository.AccountRepository;
import br.com.nszandrew.utils.TransactionValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final TransactionValidator validator;

    @Override
    @Transactional
    public AccountResponseDTO createAccount(String accountType, Long id) {
        log.info("Creating account with id {} and account info {}", id, accountType);
        Account account = new Account(accountType, id);
        repository.save(account);
        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    @Transactional
    public AccountResponseDTO depositBalanceAccount(BigDecimal balance, String id) {
        log.info("Updating balance of account with id {} and new balance is {}", id, balance);
        var account = repository.findByaccountNumber(id);
        if(account.getStatusAccount() == StatusAccount.CLOSED){throw new StatusAccountIsClosed("Please try another id");}

        BigDecimal value = account.getBalance();
        BigDecimal newBalance = value.add(balance);

        account.setBalance(newBalance);
        repository.save(account);
        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    public AccountResponseDTO withdrawBalanceAccount(BigDecimal balance, String id) {
        log.info("Withdrawing balance of account with id {} and account info {}", id, balance);
        var account = repository.findByaccountNumber(id);
        if(account.getStatusAccount() == StatusAccount.CLOSED){throw new StatusAccountIsClosed("Please try another id");}

        BigDecimal value = account.getBalance();
        if(value.compareTo(balance) < 0){throw new InsufficientFundsException("Withdraw failed, impossible to withdraw balance because your balance no have money");}

        BigDecimal newBalance = value.subtract(balance);

        account.setBalance(newBalance);
        repository.save(account);
        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    @Transactional
    public TransferTEDMoneyResponseDTO transferMoneyByTED(TransferTEDMoneyDTO transfer) {
        log.info("Transfer between two accounts {} and {}", transfer.accountNumberReceive(), transfer.accountNumberSender());
        validator.validateTransfer(transfer);

        var verifySender = repository.findByaccountNumber(transfer.accountNumberSender());
        var verifyReceiver = repository.findByaccountNumber(transfer.accountNumberReceive());

        verifySender.setBalance(verifySender.getBalance().subtract(transfer.amount()));
        repository.save(verifySender);

        verifyReceiver.setBalance(verifyReceiver.getBalance().add(transfer.amount()));
        repository.save(verifyReceiver);

        return new TransferTEDMoneyResponseDTO(transfer);
    }

    @Override
    public AccountResponseDTO cardPayment(BigDecimal balance, String accountNumber) {
        log.info("Pay card by PaymentService ");
        var account = repository.findByaccountNumber(accountNumber);

        BigDecimal value = account.getBalance();
        if(value.compareTo(balance) < 0){throw new InsufficientFundsException("Withdraw failed, impossible to withdraw balance because your balance no have money");}

        BigDecimal newBalance = value.subtract(balance);
        account.setBalance(newBalance);

        repository.save(account);

        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    public AccountResponseDTO updateAccountType(String accountType, Long id) {
        log.info("Updating account type with id {} and account info {}", id, accountType);
        var account = repository.findByidCustomer(id);
        if(account.getStatusAccount() == StatusAccount.CLOSED){throw new StatusAccountIsClosed("Please try another id");}
        account.setAccountType(AccountType.valueOf(accountType));
        repository.save(account);
        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    public AccountResponseDTO getAccount(Long id) {
        log.info("Retrieving account with id {}", id);
        var account = repository.findByidCustomer(id);
        if(account.getStatusAccount() == StatusAccount.CLOSED){throw new StatusAccountIsClosed("Please try another id");}
        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    public AccountResponseDTO getAccountByNumber(String accountNumber) {
        log.info("Retrieving account with account number {}", accountNumber);
        var account = repository.findByaccountNumber(accountNumber);
        if(account.getStatusAccount() == StatusAccount.CLOSED){throw new StatusAccountIsClosed("Please try another id");}
        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getIdCustomer(), account.getAccountType(), account.getStatusAccount(), account.getBalance(), account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    public String deleteAccount(Long id) {
        var account = repository.findByidCustomer(id);
        account.setStatusAccount(StatusAccount.CLOSED);
        repository.save(account);
        return "Account closed";
    }
}
