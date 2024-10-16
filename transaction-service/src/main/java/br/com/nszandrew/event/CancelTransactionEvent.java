package br.com.nszandrew.event;

import br.com.nszandrew.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelTransactionEvent {

    private Long id;
    private String accountOrigin;
    private String accountDestination;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String reason;

}
