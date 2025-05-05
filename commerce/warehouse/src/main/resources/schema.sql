CREATE TABLE IF NOT EXISTS warehouse_product(
    product_id UUID PRIMARY KEY,
    fragile boolean,
    width DECIMAL(10, 2) NOT NULL,
    height DECIMAL(10, 2) NOT NULL,
    depth DECIMAL(10, 2) NOT NULL,
    weight DECIMAL(10, 2) NOT NULL,
    quantity BEGIN NOT NULL
);

CREATE TABLE IF NOT EXISTS order_booking (
    order_id UUID PRIMARY KEY,
    delivery_id UUID
);

CREATE TABLE IF NOT EXISTS booking_products (
    PRIMARY KEY (order_booking_id, product_id),
    product_id UUID,
    quantity BIGINT,
    order_booking_id UUID REFERENCES order_booking(order_id)
);
