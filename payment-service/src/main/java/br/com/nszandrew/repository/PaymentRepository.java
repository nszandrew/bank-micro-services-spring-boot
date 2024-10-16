package br.com.nszandrew.repository;

import br.com.nszandrew.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByaccountNumber(String accountNumber);
}
