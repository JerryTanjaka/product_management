psql -U product_manager_user -d product_management_db

CREATE TABLE Product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    creation_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Product_category (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT fk_product
    FOREIGN KEY(product_id)
    REFERENCES Product(id)
    ON DELETE CASCADE
);
