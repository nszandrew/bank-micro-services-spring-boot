package br.com.nszandrew.service;

import br.com.nszandrew.client.account.AccountClient;
import br.com.nszandrew.client.account.StatusAccount;
import br.com.nszandrew.client.card.CardClient;
import br.com.nszandrew.client.card.CardRequestDTO;
import br.com.nszandrew.model.Credit;
import br.com.nszandrew.model.dto.CreditResponseDTO;
import br.com.nszandrew.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository repository;
    private final AccountClient client;
    private final CardClient cardClient;

    @Override
    public CreditResponseDTO setCredit(Long idCustomer) {
        log.info("Seaching credit for idCustomer = " + idCustomer);
        var account = client.getAccount(idCustomer);
        if(account.statusAccount() == StatusAccount.CLOSED || account.statusAccount() == StatusAccount.SUSPENDED || account.statusAccount() == StatusAccount.PERM_BAN){throw new RuntimeException("Account violated the bank's rules");}
        log.info("Add credit balance to a customer");
        Credit credit = new Credit(account);
        cardClient.addCard(new CardRequestDTO(credit.getAccountNumber(), credit.getDueDate(), credit.getAmountCredit()));
        repository.save(credit);
        return new CreditResponseDTO(credit.getId(), credit.getAccountNumber(), credit.getIdCustomer(), credit.getAmountCredit(), credit.getStartDate(), credit.getDueDate(), credit.getScore(), credit.getDueRate(), credit.getStatus(), credit.getCreatedAt(), credit.getUpdatedAt() );
    }

    @Override
    public CreditResponseDTO getCredit(String accountNumber) {
        log.info("Searching credit for account = " + accountNumber);
        var credit = repository.findByaccountNumber(accountNumber);
        return new CreditResponseDTO(credit.getId(), credit.getAccountNumber(), credit.getIdCustomer(), credit.getAmountCredit(), credit.getStartDate(), credit.getDueDate(), credit.getScore(), credit.getDueRate(), credit.getStatus(), credit.getCreatedAt(), credit.getUpdatedAt() );
    }

    @Override
    public void deleteCredit(String accountNumber) {
        repository.delete(repository.findByaccountNumber(accountNumber));
    }
}
