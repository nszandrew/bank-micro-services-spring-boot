package br.com.nszandrew.repository;

import br.com.nszandrew.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    Credit findByaccountNumber(String accountNumber);
}
