CREATE TABLE tb_payment (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            account_number VARCHAR(255) NOT NULL,
                            payment_type VARCHAR(50) NOT NULL,
                            amount DECIMAL(19,2) NOT NULL,
                            due_date DATE,
                            payment_date DATE NOT NULL,
                            installments INT,
                            current_installments INT,
                            installment_amount DECIMAL(19,2),
                            status VARCHAR(50) NOT NULL,
                            interest_rate DECIMAL(19,2),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
