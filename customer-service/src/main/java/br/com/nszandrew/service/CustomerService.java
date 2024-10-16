package br.com.nszandrew.service;

import br.com.nszandrew.model.dto.CustomerRequestDTO;
import br.com.nszandrew.model.dto.CustomerResponseDTO;
import br.com.nszandrew.model.dto.GetCustomerDTO;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO data);
    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO data);
    GetCustomerDTO getCustomer(Long customerId);
    void deleteCustomer(Long customerId);
}
