package br.com.nszandrew.service;

import br.com.nszandrew.client.AccountClient;
import br.com.nszandrew.client.TransferTEDMoneyDTO;
import br.com.nszandrew.event.CancelTransactionEvent;
import br.com.nszandrew.event.TransactionEvent;
import br.com.nszandrew.model.Status;
import br.com.nszandrew.model.Transaction;
import br.com.nszandrew.model.TransactionType;
import br.com.nszandrew.model.dto.TransactionRequestDTO;
import br.com.nszandrew.model.dto.TransactionResponseDTO;
import br.com.nszandrew.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final AccountClient client;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    @Override
    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO data) {
        log.info("Creating transaction");
        Transaction transaction = Transaction.builder()
                .accountOrigin(data.accountNumberSender())
                .accountDestination(data.accountNumberReceive())
                .amount(data.amount())
                .transactionType(TransactionType.TED)
                .status(Status.PROCESSING)
                .createdAt(LocalDate.now())
                .build();
        transaction.setStatus(Status.COMPLETED);
        repository.save(transaction);
        client.transactionByTED(new TransferTEDMoneyDTO(data.amount(), data.accountNumberReceive(), data.agencyReceive(), data.accountNumberSender(), data.agencySender()));

        TransactionEvent event = new TransactionEvent(transaction.getId(), transaction.getTransactionType(), transaction.getAccountDestination(), transaction.getAccountOrigin(), transaction.getAmount(), transaction.getStatus());
        log.info("Sending a transaction event {} to kafka topic transaction-service", event);
        kafkaTemplate.send("transaction-service", event);
        log.info("Completed transaction event {} to kafka topic transaction-service", event);

        return new TransactionResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO depositBalanceAccount(BigDecimal balance, String idCustomer) {
        log.info("Depositing account balance");
        Transaction transaction = Transaction.builder()
                .accountOrigin(idCustomer)
                .accountDestination("DEPOSIT")
                .amount(balance)
                .transactionType(TransactionType.DEPOSIT)
                .status(Status.PROCESSING)
                .createdAt(LocalDate.now())
                .build();
        client.depositBalanceAccount(balance, idCustomer);
        transaction.setStatus(Status.COMPLETED);

        repository.save(transaction);

        TransactionEvent event = new TransactionEvent(transaction.getId(), transaction.getTransactionType(), transaction.getAccountDestination(), transaction.getAccountOrigin(), transaction.getAmount(), transaction.getStatus());
        log.info("Sending a deposit event {} to kafka topic transaction-service", event);
        kafkaTemplate.send("transaction-service", event);
        log.info("Completed deposit event {} to kafka topic transaction-service", event);

        return new TransactionResponseDTO(transaction);
    }

    @Override
    public TransactionResponseDTO withdrawBalanceAccount(BigDecimal balance, String idCustomer) {
        log.info("Withdrawing account balance");
        Transaction transaction = Transaction.builder()
                .accountOrigin(idCustomer)
                .accountDestination("WITHDRAW")
                .amount(balance)
                .transactionType(TransactionType.WITHDRAW)
                .status(Status.PROCESSING)
                .createdAt(LocalDate.now())
                .build();
        client.withdrawBalanceAccount(balance, idCustomer);
        transaction.setStatus(Status.COMPLETED);
        repository.save(transaction);

        TransactionEvent event = new TransactionEvent(transaction.getId(), transaction.getTransactionType(), transaction.getAccountDestination(), transaction.getAccountOrigin(), transaction.getAmount(), transaction.getStatus());
        log.info("Sending a withdraw event {} to kafka topic transaction-service", event);
        kafkaTemplate.send("transaction-service", event);
        log.info("Completed withdraw event {} to kafka topic transaction-service", event);

        return new TransactionResponseDTO(transaction);
    }

    @KafkaListener(topics = "transaction-cancel-topic", groupId = "transactionServiceGroup")
    public void handleCancelTransaction(CancelTransactionEvent cancelEvent) {
        log.info("Received cancel transaction event: {}", cancelEvent);

        // Localiza a transação original no banco de dados
        Transaction transaction = repository.findById(cancelEvent.getId()).orElseThrow(() -> new RuntimeException("Transaction not found by ID " + cancelEvent.getId()));

        // Verifica o tipo de transação e realiza o cancelamento
        switch (cancelEvent.getTransactionType()) {
            case WITHDRAW:
                reverseWithdraw(transaction);
                break;
            case DEPOSIT:
                reverseDeposit(transaction);
                break;
            case TED:
                reverseTed(transaction);
                break;
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + cancelEvent.getTransactionType());
        }

        transaction.setStatus(Status.CANCELED);
        repository.save(transaction);

        log.info("Transaction {} has been successfully cancelled", cancelEvent);
    }

    // Reverter Withdraw
    private void reverseWithdraw(Transaction transaction) {
        log.info("Reversing withdraw transaction for {}", transaction.getAccountOrigin());
        client.depositBalanceAccount(transaction.getAmount(), transaction.getAccountOrigin());  // Devolve o valor para a conta de origem
    }

    // Reverter Deposit
    private void reverseDeposit(Transaction transaction) {
        log.info("Reversing deposit transaction for {}", transaction.getAccountDestination());
        client.withdrawBalanceAccount(transaction.getAmount(), transaction.getAccountDestination());  // Remove o valor da conta de destino
    }

    // Reverter TED
    private void reverseTed(Transaction transaction) {
        log.info("Reversing TED transaction from {} to {}", transaction.getAccountOrigin(), transaction.getAccountDestination());
        client.depositBalanceAccount(transaction.getAmount(), transaction.getAccountOrigin()); // Devolve para o remetente
        client.withdrawBalanceAccount(transaction.getAmount(), transaction.getAccountDestination()); // Retira do destinatário
    }
}
