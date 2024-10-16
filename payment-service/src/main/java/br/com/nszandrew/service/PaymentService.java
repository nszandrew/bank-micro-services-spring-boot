package br.com.nszandrew.service;

import br.com.nszandrew.model.Payment;
import br.com.nszandrew.model.dto.PaymentCreditRequestDTO;
import br.com.nszandrew.model.dto.PaymentDebitRequestDTO;
import br.com.nszandrew.model.dto.PaymentResponseDTO;

import java.util.Optional;

public interface PaymentService {

    PaymentResponseDTO newDebitPayment(PaymentDebitRequestDTO data);
    PaymentResponseDTO newCreditPayment(PaymentCreditRequestDTO data);
    Optional<Payment> getAllPayments(String accountNumber);
    PaymentResponseDTO getPayment(Long id);
    PaymentResponseDTO requestPay(Long id, String accountNumber);
}
