package br.com.nszandrew.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {

    private Long id;
    private String accountNumber;
    private BigDecimal amount;
    private Integer installments;
}
