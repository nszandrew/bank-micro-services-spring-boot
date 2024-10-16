package br.com.nszandrew.service;

import br.com.nszandrew.client.AccountClient;
import br.com.nszandrew.client.AccountRequestDTO;
import br.com.nszandrew.model.dto.CustomerRequestDTO;
import br.com.nszandrew.model.dto.CustomerResponseDTO;
import br.com.nszandrew.exceptions.custom.CustomerNotFoundException;
import br.com.nszandrew.model.Customer;
import br.com.nszandrew.model.dto.GetCustomerDTO;
import br.com.nszandrew.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final AccountClient client;
    private final CacheService cacheService;

    @Override
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO data) {
        log.info("Create customer with customerRequestDTO: {}", data);

        Customer customer = Customer.builder()
                .fullName(data.fullName())
                .email(data.email())
                .dateOfBirth(data.dateOfBirth())
                .cpf(data.cpf())
                .phone(data.phone())
                .address(data.address())
                .createAt(LocalDateTime.now())
                .updateAt(null)
                .build();

        repository.save(customer);

        client.created(new AccountRequestDTO("CHECKING", customer.getId()));

        cacheService.evictAllCacheValues("customer");
        return new CustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO data) {
        log.info("Updating a customer: {}", data);
        var updateCustomer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException("id de cliente nao encontrado ou nao existe " + id));
        updateCustomer.updateCustomer(data);
        cacheService.evictAllCacheValues("customer");
        return new CustomerResponseDTO(updateCustomer);
    }

    @Override
    @Cacheable("customer")
    public GetCustomerDTO getCustomer(Long customerId) {
        log.info("Retrieving customer with customerId: {}", customerId);
        var findCustomer = repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("id de cliente nao encontrado " + customerId));

        return MapperModel.parseObject(findCustomer, GetCustomerDTO.class);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        log.info("Deleting customer with customerId: {}", customerId);
        repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("id de cliente nao encontrado " + customerId));
        client.delete(customerId);
        cacheService.evictAllCacheValues("customer");
        repository.deleteById(customerId);
    }
}
