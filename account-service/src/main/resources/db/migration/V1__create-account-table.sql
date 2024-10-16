CREATE TABLE tb_account (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         account_number VARCHAR(100) NOT NULL,
                         agency VARCHAR(3000) NOT NULL,
                         id_customer BIGINT NOT NULL,
                         account_type VARCHAR(20) NOT NULL,
                         status_account VARCHAR(20) NOT NULL,
                         balance DECIMAL(19,2) DEFAULT 0.00,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
