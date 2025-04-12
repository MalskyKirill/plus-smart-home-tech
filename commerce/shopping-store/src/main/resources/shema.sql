CREATE TABLE IF NOT EXISTS products {
    product_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(55) NOT NULL,
    description VARCHAR(255) NOT NULL,
    image_src VARCHAR(1000),
    quantity_state VARCHAR(55) NOT NULL,
    product_state VARCHAR(55) NOT NULL,
    product_category VARCHAR(55),
    price DOUBLE PRECISION
}
