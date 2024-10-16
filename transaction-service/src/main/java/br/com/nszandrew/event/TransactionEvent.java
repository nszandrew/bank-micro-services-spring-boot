package br.com.nszandrew.event;

import br.com.nszandrew.model.Status;
import br.com.nszandrew.model.TransactionType;
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
