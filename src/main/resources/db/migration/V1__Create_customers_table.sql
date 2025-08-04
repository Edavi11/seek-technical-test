CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    birth_date DATE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE INDEX idx_customers_first_name ON customers(first_name);
CREATE INDEX idx_customers_last_name ON customers(last_name);
CREATE INDEX idx_customers_age ON customers(age);
CREATE INDEX idx_customers_birth_date ON customers(birth_date);
CREATE INDEX idx_customers_is_deleted ON customers(is_deleted);
CREATE INDEX idx_customers_created_at ON customers(created_at); 