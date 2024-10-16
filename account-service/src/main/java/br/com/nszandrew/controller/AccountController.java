package br.com.nszandrew.controller;

import br.com.nszandrew.model.dto.AccountRequestDTO;
import br.com.nszandrew.model.dto.AccountResponseDTO;
import br.com.nszandrew.model.dto.TransferTEDMoneyDTO;
import br.com.nszandrew.model.dto.TransferTEDMoneyResponseDTO;
import br.com.nszandrew.service.AccountService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @Hidden
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO data) {
        var newAccount = service.createAccount(data.accountType(), data.id());
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping("/ted")
    @Hidden
    public ResponseEntity<TransferTEDMoneyResponseDTO> createTEDtransaction(@RequestBody @Valid TransferTEDMoneyDTO data) {
        var newTed = service.transferMoneyByTED(data);
        return new ResponseEntity<>(newTed, HttpStatus.CREATED);
    }

    @PutMapping("/deposit")
    @Hidden
    public ResponseEntity<AccountResponseDTO> depositBalanceAccount(@RequestParam BigDecimal balance, @RequestParam String idCustomer) {
        var newBalance = service.depositBalanceAccount(balance, idCustomer);
        return new ResponseEntity<>(newBalance, HttpStatus.OK);
    }

    @PutMapping("/withdraw")
    @Hidden
    public ResponseEntity<AccountResponseDTO> withdrawBalanceAccount(@RequestParam BigDecimal balance, @RequestParam String idCustomer) {
        var newBalance = service.withdrawBalanceAccount(balance, idCustomer);
        return new ResponseEntity<>(newBalance, HttpStatus.OK);
    }

    @PutMapping("/card")
    @Hidden
    public ResponseEntity<AccountResponseDTO> cardPayment(@RequestParam BigDecimal balance, @RequestParam String accountNumber) {
        var cardBalance = service.cardPayment(balance, accountNumber);
        return new ResponseEntity<>(cardBalance, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AccountResponseDTO> updateAccountType(@RequestParam String accountType, @RequestParam Long idCustomer) {
        var newAccountType = service.updateAccountType(accountType, idCustomer);
        return new ResponseEntity<>(newAccountType, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<AccountResponseDTO> getAccount(@RequestParam Long idCustomer) {
        var account = service.getAccount(idCustomer);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/accountnumber")
    public ResponseEntity<AccountResponseDTO> getAccount(@RequestParam String accountNumber) {
        var account = service.getAccountByNumber(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestBody Long idCustomer) {
        return new ResponseEntity<>(service.deleteAccount(idCustomer), HttpStatus.NO_CONTENT);
    }
}
