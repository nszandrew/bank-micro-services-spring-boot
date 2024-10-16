package br.com.nszandrew.repository;

import br.com.nszandrew.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByidCustomer(Long id);

    Account findByaccountNumber(String sender);

    Account findByagency(String type);
}
