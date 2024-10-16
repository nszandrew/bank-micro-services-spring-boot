CREATE TABLE tb_customer (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             full_name VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL,
                             date_of_birth DATE NOT NULL,
                             cpf VARCHAR(11) NOT NULL,
                             phone VARCHAR(20),
                             address VARCHAR(255),
                             create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
