package br.com.nszandrew.controller;

import br.com.nszandrew.model.dto.CustomerRequestDTO;
import br.com.nszandrew.model.dto.CustomerResponseDTO;
import br.com.nszandrew.model.dto.GetCustomerDTO;
import br.com.nszandrew.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customer) {
        var newCustomer = service.createCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomerDTO> getCustomer(@PathVariable Long id) {
        var newCustomer = service.getCustomer(id);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequestDTO customer) {
        var updateCustomer = service.updateCustomer(id, customer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
