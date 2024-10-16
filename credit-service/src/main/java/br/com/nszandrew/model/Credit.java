package br.com.nszandrew.model;

import br.com.nszandrew.client.account.AccountResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "tb_credit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;
    @Column(unique = true)
    private Long idCustomer;
    private BigDecimal amountCredit; // Valor total do crédito concedido
    private LocalDate startDate; // Data de início do crédito
    private LocalDate dueDate; // Data final para pagamento
    private Integer score;
    private LocalDate dueRate; //Data de vencimento da parcela
    @Enumerated(EnumType.STRING)
    private CreditStatus status;
    @CreationTimestamp
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDate updatedAt;

    public Credit(AccountResponseDTO account) {
        this.accountNumber = account.accountNumber();
        this.idCustomer = account.idCustomer();
        this.amountCredit = getAmountCredit(getScore(account.balance().doubleValue()), account.balance());
        this.score = getScore(account.balance().doubleValue());
        this.dueDate = LocalDate.now().plusMonths(1);
        this.dueRate = LocalDate.now().plusMonths(1);
        this.startDate = LocalDate.now();
        this.status = CreditStatus.ACTIVE;
        this.createdAt = LocalDate.now();
        this.updatedAt = null;
    }

    public BigDecimal getAmountCredit(Integer score, BigDecimal balance ){
        BigDecimal baseCredit = BigDecimal.ZERO;

        // Definindo faixas de score para determinar o valor base do crédito
        if (score >= 900){
          baseCredit = balance.multiply(new BigDecimal("5.0")); //Credito vai ser 5x oque ele tem na conta
        } else if (score >= 800) {
            baseCredit = balance.multiply(new BigDecimal("2.0")); // Crédito pode ser o dobro do saldo
        } else if (score >= 600) {
            baseCredit = balance.multiply(new BigDecimal("1.5")); // Crédito de 150% do saldo
        } else if (score >= 300) {
            baseCredit = balance.multiply(new BigDecimal("1.2")); // Crédito de 120% do saldo
        } else if (score >= 100) {
            baseCredit = balance.multiply(new BigDecimal("1.0")); // Crédito igual ao saldo
        } else {
            baseCredit = balance.multiply(new BigDecimal("0.5")); // Crédito é 50% do saldo para scores baixos
        }

        BigDecimal maxCreditLimit = BigDecimal.ZERO;

        if (score >= 800 && score <= 899) {
            maxCreditLimit = new BigDecimal("50000"); // Máximo de crédito de 50k
        } else if (score >= 600) {
            maxCreditLimit = new BigDecimal("30000"); // Máximo de crédito de 30k
        } else if (score >= 300) {
            maxCreditLimit = new BigDecimal("15000"); // Máximo de crédito de 15k
        } else {
            maxCreditLimit = new BigDecimal("5000");  // Máximo de crédito de 5k
        }

        return baseCredit.min(maxCreditLimit);
    }

    public Integer getScore(Double balance){
        Random random = new Random();

        if(balance >= 10000.00){
            return 800 + random.nextInt(200);
        }
        if(balance <= 9999.00 && balance >= 5000.00){
            return 600 + random.nextInt(200);
        }
        if(balance <= 4999.99 && balance >= 1000.00){
            return 300 + random.nextInt(200);
        }
        if(balance <= 999.99 && balance >= 100.00){
            return 100 + random.nextInt(200);
        }
        if(balance <= 99.99 && balance >= 10.00){
            return 50 + random.nextInt(200);
        }

        return 49;
    }
}
