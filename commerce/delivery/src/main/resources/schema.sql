CREATE TABLE IF NOT EXISTS addresses (
    address_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    country VARCHAR(55) NOT NULL,
    city VARCHAR(55) NOT NULL,
    street VARCHAR(55) NOT NULL,
    house VARCHAR(55) NOT NULL,
    flat VARCHAR(55) NOT NULL
);

CREATE TABLE IF NOT EXISTS delivery (
    delivery_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id UUID NOT NULL,
    from_address_id UUID NOT NULL,
    to_address_id UUID NOT NULL,
    state VARCHAR(55) NOT NULL,
    delivery_weight DECIMAL(10, 2),
    delivery_volume DECIMAL(10, 2),
    fragile boolean,
    CONSTRAINT delivery_from_address_fk FOREIGN KEY(from_address_id) REFERENCES addresses(address_id) ON DELETE CASCADE,
    CONSTRAINT delivery_to_address_id_fk FOREIGN KEY(to_address_id) REFERENCES addresses(address_id) ON DELETE CASCADE
);
