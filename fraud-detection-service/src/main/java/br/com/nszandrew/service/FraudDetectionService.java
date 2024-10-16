package br.com.nszandrew.service;

import br.com.nszandrew.event.PaymentEvent;
import br.com.nszandrew.event.TransactionEvent;
import br.com.nszandrew.event.CancelTransactionEvent;
import br.com.nszandrew.utils.FraudDetection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionService {

    private final FraudDetection fraud;
    private final KafkaTemplate<String, CancelTransactionEvent> kafkaTemplate;
    private final KafkaTemplate<String, PaymentEvent> paymentKafkaTemplate;

    @KafkaListener(topics = "transaction-service", groupId = "transactionService-fraud")
    public void listenTransaction(TransactionEvent event) {
        log.info("Received transaction event: {}", event);

        Boolean isFraudulent = fraud.isFraudulent(event);

        if (isFraudulent) {
            log.warn("Fraud detected for transaction: {}", event.getId());
            sendCancelTransactionEvent(event);
        }
    }

    @KafkaListener(topics = "payment-service", groupId = "paymentService-fraud")
    public void listenPayment(PaymentEvent event) {
        log.info("Received payment event: {}", event);

        Boolean isFradulent = fraud.isFraudulent(event);

        if(isFradulent){
            log.warn("Fraud detected for card payment: {}", event.getId());
            paymentKafkaTemplate.send("payment-cancel-topic", event);
            log.info("Cancel card payment: {}", event.getId());
        }

    }

    private void sendCancelTransactionEvent(TransactionEvent transaction) {
        CancelTransactionEvent cancelEvent = new CancelTransactionEvent(
                transaction.getId(),
                transaction.getAccountOrigin(),
                transaction.getAccountDestination(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                "Fraud detected"
        );

        kafkaTemplate.send("transaction-cancel-topic", cancelEvent);
        log.info("Cancel event sent for transaction: {}", transaction);
    }
}
