package br.com.nszandrew.controller;

import br.com.nszandrew.model.dto.TransactionRequestDTO;
import br.com.nszandrew.model.dto.TransactionResponseDTO;
import br.com.nszandrew.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/ted")
    public ResponseEntity<TransactionResponseDTO> addTransaction(@RequestBody @Valid TransactionRequestDTO data) {
        var transaction = service.createTransaction(data);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponseDTO> depositTransaction(@RequestParam BigDecimal balance, @RequestParam String idCustomer) {
        var transaction = service.depositBalanceAccount(balance, idCustomer);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponseDTO> withdrawTransaction(@RequestParam BigDecimal balance, @RequestParam String idCustomer) {
        var transaction = service.withdrawBalanceAccount(balance, idCustomer);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
