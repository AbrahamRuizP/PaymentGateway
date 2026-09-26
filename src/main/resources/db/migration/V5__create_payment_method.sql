
CREATE TABLE payment_method (
    id UUID,
    type VARCHAR(25) NOT NULL,
    provider VARCHAR(255),
    brand VARCHAR(100),
    last4 VARCHAR(4),
    provider_payment_method_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    customer_id UUID NOT NULL,

    CONSTRAINT pk_payment_method PRIMARY KEY (id),

    CONSTRAINT fk_payment_method_customer
    FOREIGN KEY (customer_id)
    REFERENCES customer (id)
);