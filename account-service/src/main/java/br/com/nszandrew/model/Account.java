package br.com.nszandrew.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "tb_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(unique = true, nullable = false)
    private String agency;

    @Column(nullable = false)
    private Long idCustomer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusAccount statusAccount;

    @Column(nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Account(String accountType, Long id){
        this.accountNumber =  randomAccountNumber();
        this.agency = randomAgencyNumber();
        this.idCustomer = id;
        this.accountType = AccountType.valueOf(accountType);
        setStatusAccount(StatusAccount.ACTIVE);
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public String randomAccountNumber(){
        Random random = new Random();
        int anotherRandomNumber = 100000 + random.nextInt(999999);
        int randomNumber = 1 + random.nextInt(9);
        return anotherRandomNumber + "-" + randomNumber;
    }
    public String randomAgencyNumber(){
        Random random = new Random();
        int randomNumber = 1 + random.nextInt(201);
        int anotherRandomNumber = 1 + random.nextInt(9);
        return randomNumber + "" + anotherRandomNumber;
    }
}
