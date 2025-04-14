DROP TABLE IF EXISTS cart_products, cart;

CREATE TABLE IF NOT EXISTS cart (
    cart_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_name varchar(55) NOT NULL
    state VARCHAR(25) NOT NULL,
);

CREATE TABLE IF NOT EXISTS cart_products (
    cart_id UUID,
    product_id UUID,
    quantity BIGINT,
    CONSTRAINT cart_products_pk PRIMARY KEY (cart_id, product_id),
    CONSTRAINT cart_products_cart_fk FOREIGN KEY(cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE
);
