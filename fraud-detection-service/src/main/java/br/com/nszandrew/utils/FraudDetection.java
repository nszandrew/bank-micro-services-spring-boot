package br.com.nszandrew.utils;

import br.com.nszandrew.event.PaymentEvent;
import br.com.nszandrew.event.Status;
import br.com.nszandrew.event.TransactionEvent;
import br.com.nszandrew.event.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
public class FraudDetection {

    public Boolean isFraudulent(TransactionEvent transactionEvent) {
        log.info("Checking fraud for transaction event: {}", transactionEvent);


        if (isSuspiciousAmount(transactionEvent.getAmount(), transactionEvent.getTransactionType())) {
            log.warn("Transaction event flagged as fraudulent due to suspicious amount: {}", transactionEvent.getAmount());
            return true;
        }

        // 2. Verificação de transações entre a mesma conta (origem e destino iguais)
        if (transactionEvent.getAccountOrigin().equals(transactionEvent.getAccountDestination())) {
            log.warn("Transaction event flagged as fraudulent due to same origin and destination accounts: {}", transactionEvent.getAccountOrigin());
            return true;
        }

        // 3. Verificação de status de transação inválido
        if (isSuspiciousStatus(transactionEvent.getStatus())) {
            log.warn("Transaction event flagged as fraudulent due to invalid status: {}", transactionEvent.getStatus());
            return true;
        }

        // 4. Verificação de valores inconsistentes para diferentes tipos de transações (exemplo: TED tem limites específicos)
        if (isInconsistentAmountForTransactionType(transactionEvent.getAmount(), transactionEvent.getTransactionType())) {
            log.warn("Transaction event flagged as fraudulent due to inconsistent amount for transaction type: {} with amount: {}", transactionEvent.getTransactionType(), transactionEvent.getAmount());
            return true;
        }

        log.info("Transaction event {} passed all fraud checks.", transactionEvent);
        return false; // Não foi detectada fraude
    }

    public boolean isFraudulent(PaymentEvent paymentEvent) {
        // Verificar se o número da conta é nulo ou inválido
        if (paymentEvent.getAccountNumber() == null || paymentEvent.getAccountNumber().isEmpty()) {
            log.warn("Fraude detectada: Número de conta inválido ou nulo.");
            return true;
        }

        // Verificar se o valor da transação é negativo ou zero
        if (paymentEvent.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Fraude detectada: Montante da transação é inválido. Montante: {}", paymentEvent.getAmount());
            return true;
        }

        // Verificar se o número de parcelas é menor que 1
        if (paymentEvent.getInstallments() == null || paymentEvent.getInstallments() < 1) {
            log.warn("Fraude detectada: Número de parcelas inválido. Parcelas: {}", paymentEvent.getInstallments());
            return true;
        }

        // Verificar se o valor das parcelas excede o limite permitido
        BigDecimal maxInstallmentValue = new BigDecimal("10000");  // Limite de segurança para cada parcela
        BigDecimal installmentValue = paymentEvent.getAmount().divide(new BigDecimal(paymentEvent.getInstallments()), RoundingMode.HALF_UP);

        if (installmentValue.compareTo(maxInstallmentValue) > 0) {
            log.warn("Fraude detectada: Valor de parcela excede o limite permitido. Valor da parcela: {}, Limite: {}", installmentValue, maxInstallmentValue);
            return true;
        }

        // Verificar transações muito grandes (ex: acima de um certo limite)
        BigDecimal maxTransactionAmount = new BigDecimal("50000");  // Exemplo de limite
        if (paymentEvent.getAmount().compareTo(maxTransactionAmount) > 0) {
            log.warn("Fraude detectada: Valor da transação excede o limite máximo permitido. Montante: {}, Limite: {}", paymentEvent.getAmount(), maxTransactionAmount);
            return true;
        }

        // Outras verificações podem ser adicionadas aqui, como a verificação de padrões de transações incomuns

        // Se nenhuma fraude for detectada, retorna false
        log.info("Nenhuma fraude detectada para a transação com a conta: {}", paymentEvent.getAccountNumber());
        return false;
    }

    // Verifica se o valor da transação é considerado suspeito para o tipo de transação
    private boolean isSuspiciousAmount(BigDecimal amount, TransactionType transactionType) {
        BigDecimal MAX_TRANSACTION_AMOUNT = new BigDecimal("100000"); // Limite arbitrário para grandes transações
        BigDecimal MIN_TRANSACTION_AMOUNT = new BigDecimal("00.01"); // Mínimo aceitável

        if (transactionType == TransactionType.TED) {
            // Limites diferentes para TED
            BigDecimal MAX_TED_AMOUNT = new BigDecimal("10000");
            BigDecimal MIN_TED_AMOUNT = new BigDecimal("100");
            return amount.compareTo(MAX_TED_AMOUNT) > 0 || amount.compareTo(MIN_TED_AMOUNT) < 0;
        }

        // Para outros tipos de transação
        return amount.compareTo(MAX_TRANSACTION_AMOUNT) > 0 || amount.compareTo(MIN_TRANSACTION_AMOUNT) < 0;
    }

    // Verifica se o status da transação é suspeito
    private boolean isSuspiciousStatus(Status status) {
        // Considerar apenas status "COMPLETED" e "PROCESSING" como válidos
        return status != Status.COMPLETED && status != Status.PROCESSING;
    }

    // Verifica se o valor é inconsistente com o tipo de transação
    private boolean isInconsistentAmountForTransactionType(BigDecimal amount, TransactionType transactionType) {
        // Exemplo: TEDs geralmente têm um valor mínimo e máximo, enquanto depósitos e saques não têm o mesmo limite
        if (transactionType == TransactionType.TED) {
            BigDecimal MIN_TED_AMOUNT = new BigDecimal("100");
            BigDecimal MAX_TED_AMOUNT = new BigDecimal("10000");
            return amount.compareTo(MIN_TED_AMOUNT) < 0 || amount.compareTo(MAX_TED_AMOUNT) > 0;
        }

        if (transactionType == TransactionType.WITHDRAW) {
            BigDecimal MAX_WITHDRAW_AMOUNT = new BigDecimal("5000"); // Limite arbitrário para saques
            return amount.compareTo(MAX_WITHDRAW_AMOUNT) > 0;
        }

        // Outras transações não têm limites especiais
        return false;
    }

}
