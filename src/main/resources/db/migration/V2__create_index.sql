CREATE INDEX idx_payment_merchant_id
ON payment (merchant_id);

CREATE INDEX idx_payment_customer_id
ON payment (customer_id);

CREATE INDEX idx_payment_created_at
ON payment (created_at);

CREATE INDEX idx_payment_status
ON payment (status);