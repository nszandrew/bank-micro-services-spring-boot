package br.com.nszandrew.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEvent {

    private Long id;
    private TransactionType transactionType;
    private String accountDestination;
    private String accountOrigin;
    private BigDecimal amount;
    private Status status;
}
