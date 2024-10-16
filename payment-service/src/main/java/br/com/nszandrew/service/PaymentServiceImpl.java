package br.com.nszandrew.service;

import br.com.nszandrew.client.account.AccountClient;
import br.com.nszandrew.client.credit.CreditClient;
import br.com.nszandrew.event.PaymentEvent;
import br.com.nszandrew.exceptions.custom.AccountNotFoundException;
import br.com.nszandrew.exceptions.custom.MaximumLimitException;
import br.com.nszandrew.exceptions.custom.PaymentAlreadyCompletedException;
import br.com.nszandrew.exceptions.custom.PaymentNotFoundException;
import br.com.nszandrew.model.Payment;
import br.com.nszandrew.model.PaymentType;
import br.com.nszandrew.model.Status;
import br.com.nszandrew.model.dto.PaymentCreditRequestDTO;
import br.com.nszandrew.model.dto.PaymentDebitRequestDTO;
import br.com.nszandrew.model.dto.PaymentResponseDTO;
import br.com.nszandrew.repository.PaymentRepository;
import br.com.nszandrew.utils.CalculatedInterest;
import br.com.nszandrew.utils.Verify;
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
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository repository;
    private final CalculatedInterest interest;
    private final Verify verify;
    private final AccountClient client;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    private final CreditClient creditClient;


    @Override
    public PaymentResponseDTO newDebitPayment(PaymentDebitRequestDTO data) {
        log.info("New payment debit called");
        Payment payment = Payment.builder()
                .accountNumber(data.accountNumber())
                .amount(data.amount())
                .installmentAmount(data.amount())
                .currentInstallments(1)
                .installments(1)
                .dueDate(LocalDate.now().plusMonths(1))
                .paymentType(PaymentType.DEBIT)
                .paymentDate(LocalDate.now())
                .status(Status.LATE)
                .build();

        var account = client.getAccount(data.accountNumber());
        if(!(account.accountNumber().equals(data.accountNumber()))) {throw new AccountNotFoundException("Account not found try with another account number" + account.accountNumber());}

        repository.save(payment);

        PaymentEvent event = new PaymentEvent(payment.getId(), payment.getAccountNumber(), payment.getAmount(), payment.getInstallments());
        log.info("Sending a debit payment event {} to kafka topic transaction-service", event);
        kafkaTemplate.send("payment-service", event);
        log.info("Completed debit payment event {} to kafka topic transaction-service", event);

        return new PaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO newCreditPayment(PaymentCreditRequestDTO data) {
        log.info("New payment credit called");
        Payment payment = Payment.builder()
                .accountNumber(data.accountNumber())
                .amount(data.amount())
                .paymentType(PaymentType.CREDIT)
                .paymentDate(LocalDate.now().plusMonths(1))
                .dueDate(LocalDate.now().plusMonths(1))
                .installments(data.installments())
                .currentInstallments(1)
                .interestRate(BigDecimal.ZERO)
                .status(Status.LATE)
                .build();

        double installment =  data.amount().doubleValue() / data.installments();
        payment.setInstallmentAmount(BigDecimal.valueOf(installment));

        var response = client.getAccount(data.accountNumber());
        if(!(response.accountNumber().equals(data.accountNumber()))) {throw new AccountNotFoundException("Account not found try with another account number" + response.accountNumber());}

        var credit = creditClient.getCredits(data.accountNumber());
        if (credit.amountCredit() == null || data.amount() == null) {throw new IllegalArgumentException("Credit amount or requested amount is null");}
        if (credit.amountCredit().compareTo(data.amount()) < 0) {throw new MaximumLimitException("Limit exceeded maximum amount credit " + credit.amountCredit());}

        repository.save(payment);

        PaymentEvent event = new PaymentEvent(payment.getId(), payment.getAccountNumber(), payment.getAmount() ,payment.getInstallments());
        log.info("Sending a credit payment event {} to kafka topic transaction-service", event);
        kafkaTemplate.send("payment-service", event);
        log.info("Completed credit payment event {} to kafka topic transaction-service", event);
        return new PaymentResponseDTO(payment);
    }

    @Override
    public Optional<Payment> getAllPayments(String accountNumber) {
        log.info("Get all payments by this account number {}", accountNumber);

        return repository.findByaccountNumber(accountNumber);
    }

    @Override
    public PaymentResponseDTO getPayment(Long id) {
        log.info("Get payment by this id {}", id);
        var payment = repository.findById(id).get();

        return new PaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO requestPay(Long id, String accountNumber) {
        var payment = repository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found by ID " + id));

        if (!payment.getAccountNumber().equals(accountNumber)) {throw new AccountNotFoundException("Account not found by account number " + accountNumber);}
        if (payment.getStatus() == Status.PAID) {throw new PaymentAlreadyCompletedException("Payment already completed for this account.");}

        if(payment.getInstallments().equals(payment.getCurrentInstallments())){
            payment.setStatus(Status.PAID);
        }

        BigDecimal totalAmount = payment.getInstallmentAmount();
        if (payment.getDueDate().isBefore(LocalDate.now())) {
            totalAmount = totalAmount.add(interest.calculateInterest(payment));
            payment.setStatus(Status.LATE);
        }

        verify.extracted(payment);

        client.withdraw(payment.getInstallmentAmount(), payment.getAccountNumber());

        repository.save(payment);
        return new PaymentResponseDTO(payment);
    }

    @KafkaListener(topics = "payment-cancel-topic", groupId = "paymentServiceGroup")
    public void deletePayment(PaymentEvent event) {
        var delete = repository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Payment not find by id: " + event.getId()));
        repository.delete(delete);
    }

}
