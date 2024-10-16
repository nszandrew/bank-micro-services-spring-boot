package br.com.nszandrew.repository;

import br.com.nszandrew.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByAccountOrigin(String accountOrigin);
}
