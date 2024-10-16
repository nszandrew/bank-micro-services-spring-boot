package br.com.nszandrew.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_payment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull
    private BigDecimal amount;

    private LocalDate dueDate; //Data de vencimento

    @NotNull
    private LocalDate paymentDate;

    private Integer installments; //Numero total de parcelas
    private Integer currentInstallments; //Parcela atual
    private BigDecimal installmentAmount; //installmentAmount

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal interestRate; //Taxa de juros se estiver com pagamento atrasado
}
