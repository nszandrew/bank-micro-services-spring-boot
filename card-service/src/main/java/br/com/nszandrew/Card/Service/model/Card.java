package br.com.nszandrew.Card.Service.model;

import br.com.nszandrew.Card.Service.model.dto.CardRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "tb_card")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CardType cardType;

    @NotNull
    private String accountNumber;

    @NotNull
    private BigDecimal creditLimit;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @NotNull
    private LocalDate expiryDate;

    @NotNull
    private Integer securityCode;

    //Arrumar logica da data de vencimento da fatura
    private LocalDate dueDate;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    public Card(CardRequestDTO data){
        this.accountNumber = data.accountNumber();
        this.dueDate = data.dueDate();
        this.creditLimit = data.creditLimit();
        this.cardNumber = randomCardNumber();
        this.cardType = CardType.VIRTUAL;
        this.status = Status.ACTIVE;
        this.expiryDate = LocalDate.now().plusYears(5).plusMonths(5).plusDays(2);
        this.securityCode = randomSecurityCode();
        this.createdAt = LocalDate.now();
        this.updatedAt = null;
    }

    public String randomCardNumber(){
        Random random = new Random();
        var fistFour = 1000 + random.nextInt(9999);
        var secondFour = 1000 + random.nextInt(9999);
        var thirdFour = 1000 + random.nextInt(9999);
        var fourFour = 1000 + random.nextInt(9999);

        return fistFour + "-" + secondFour + "-" + thirdFour + "-" + fourFour;
    }

    public Integer randomSecurityCode(){
        Random random = new Random();
        return 100 + random.nextInt(999);
    }
}
