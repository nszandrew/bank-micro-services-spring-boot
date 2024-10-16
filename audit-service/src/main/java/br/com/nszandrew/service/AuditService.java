package br.com.nszandrew.service;

import br.com.nszandrew.event.PaymentEvent;
import br.com.nszandrew.event.TransactionEvent;
import br.com.nszandrew.model.Audit;
import br.com.nszandrew.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditRepository repository;

    @KafkaListener(topics = "payment-service", groupId = "paymentService-audit")
    public void consumePaymentEvent(PaymentEvent paymentEvent) {
        Audit auditLog = new Audit();
        auditLog.setEventType("PAYMENT");
        auditLog.setEntityId(String.valueOf(paymentEvent.getId()));
        auditLog.setAccountNumber(paymentEvent.getAccountNumber());
        auditLog.setAmount(paymentEvent.getAmount());
        auditLog.setInstallments(paymentEvent.getInstallments());
        auditLog.setTimestamp(LocalDate.now());

        repository.save(auditLog);
        log.info("Payment event auditado: {}", paymentEvent);
    }

    @KafkaListener(topics = "transaction-service", groupId = "transactionService-audit")
    public void consumeTransactionEvent(TransactionEvent transactionEvent) {
        Audit auditLog = new Audit();
        auditLog.setEventType("TRANSACTION");
        auditLog.setEntityId(String.valueOf(transactionEvent.getId()));
        auditLog.setAccountNumber(transactionEvent.getAccountOrigin());
        auditLog.setEventType(transactionEvent.getTransactionType().toString());
        auditLog.setAmount(transactionEvent.getAmount());
        auditLog.setDetails(String.valueOf(transactionEvent.getStatus()));
        auditLog.setTimestamp(LocalDate.now());

        repository.save(auditLog);
        log.info("Transaction event auditado: {}", transactionEvent);
    }
}
