package br.com.nszandrew.client.credit;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditResponseDTO(Long id,
                                String accountNumber,
                                Long idCustomer,
                                BigDecimal amountCredit,
                                LocalDate startDate,
                                LocalDate dueDate,
                                Integer score,
                                LocalDate dueRate,
                                CreditStatus status,
                                LocalDate createdAt,
                                LocalDate updatedAt
) {
}
