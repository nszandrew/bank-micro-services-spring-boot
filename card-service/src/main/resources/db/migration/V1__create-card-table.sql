CREATE TABLE tb_card (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         card_number VARCHAR(255) UNIQUE NOT NULL,
                         card_type VARCHAR(20) NOT NULL,
                         account_number VARCHAR(255) NOT NULL,
                         credit_limit DECIMAL(19, 2) NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         expiry_date DATE NOT NULL,
                         security_code INT NOT NULL,
                         due_date DATE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
