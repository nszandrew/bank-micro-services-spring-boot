package br.com.nszandrew.controller;

import br.com.nszandrew.model.dto.CreditResponseDTO;
import br.com.nszandrew.service.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    private final CreditService service;

    public CreditController(CreditService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreditResponseDTO> offerCredit(@RequestParam Long idCustomer) {
        var addCreditBalance = service.setCredit(idCustomer);
        return new ResponseEntity<>(addCreditBalance, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CreditResponseDTO> getCredits(@RequestParam String accountNumber) {
        var getCustomerCredit = service.getCredit(accountNumber);
        return new ResponseEntity<>(getCustomerCredit, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteCredit(@RequestParam String accountNumber) {
        service.deleteCredit(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
