CREATE TABLE tb_transactions (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              account_destination VARCHAR(255) NOT NULL,
                              account_origin VARCHAR(255) NOT NULL,
                              amount DECIMAL(19,2) NOT NULL,
                              transaction_type VARCHAR(50) NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
