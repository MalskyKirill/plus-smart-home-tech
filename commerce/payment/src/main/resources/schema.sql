CREATE TABLE IF NOT EXISTS payments (
    payment_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id UUID NOT NULL,
    state VARCHAR(55) NOT NULL,
    total_payment DECIMAL(10, 2),
    delivery_total DECIMAL(10, 2),
    fee_total DECIMAL(10, 2)
);
