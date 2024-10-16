package br.com.nszandrew.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "auditlogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @Id
    private String id;
    private String eventType;
    private String accountNumber;
    private BigDecimal amount;
    private Integer installments;
    private String action;
    private String entityId;
    private LocalDate timestamp;
    private String details;
}
