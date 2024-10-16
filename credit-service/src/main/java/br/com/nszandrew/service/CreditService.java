package br.com.nszandrew.service;

import br.com.nszandrew.model.dto.CreditResponseDTO;

public interface CreditService {

    CreditResponseDTO setCredit(Long idCustomer);
    CreditResponseDTO getCredit(String accountNumber);
    void deleteCredit(String accountNumber);

}
