package br.com.nszandrew.controller;

import br.com.nszandrew.model.Payment;
import br.com.nszandrew.model.dto.PaymentCreditRequestDTO;
import br.com.nszandrew.model.dto.PaymentDebitRequestDTO;
import br.com.nszandrew.model.dto.PaymentResponseDTO;
import br.com.nszandrew.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/debit")
    public ResponseEntity<PaymentResponseDTO> debitPayment(@RequestBody PaymentDebitRequestDTO data) {
        var newDebitPayment = service.newDebitPayment(data);
        return new ResponseEntity<>(newDebitPayment, HttpStatus.CREATED);
    }

    @PostMapping("/credit")
    public ResponseEntity<PaymentResponseDTO> creditPayment(@RequestBody PaymentCreditRequestDTO data) {
        var newCreditPayment = service.newCreditPayment(data);
        return new ResponseEntity<>(newCreditPayment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Optional<Payment>> getAllPayments(@RequestParam String accountNumber) {
        var getAllPay = service.getAllPayments(accountNumber);
        return new ResponseEntity<>(getAllPay, HttpStatus.OK);
    }

    @PutMapping("/pay")
    public ResponseEntity<PaymentResponseDTO> requestPay(@RequestParam Long id, @RequestParam String accountNumber) {
        var pay = service.requestPay(id, accountNumber);

        return new ResponseEntity<>(pay, HttpStatus.OK);
    }
}
