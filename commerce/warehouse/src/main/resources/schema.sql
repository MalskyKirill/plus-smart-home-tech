CREATE TABLE IF NOT EXISTS warehouse_product(
    product_id UUID PRIMARY KEY,
    fragile boolean,
    width DECIMAL(10, 2) NOT NULL,
    height DECIMAL(10, 2) NOT NULL,
    depth DECIMAL(10, 2) NOT NULL,
    weight DECIMAL(10, 2) NOT NULL,
    quantity BEGIN NOT NULL
);
