-- ==================================================
-- Table: users
-- Purpose: Store application users.
-- ==================================================
CREATE TABLE users (
                       id UUID NOT NULL,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email VARCHAR(255),
                       reset_token VARCHAR(255),
                       password_hash VARCHAR(255),
                       phone VARCHAR(255),
                       username VARCHAR(255) NOT NULL,
                       user_role VARCHAR(50),

                       account_non_expired BOOLEAN NOT NULL,
                       account_non_locked BOOLEAN NOT NULL,
                       credentials_non_expired BOOLEAN NOT NULL,
                       enabled BOOLEAN NOT NULL,

                       CONSTRAINT pk_users PRIMARY KEY (id),
                       CONSTRAINT uk_users_username UNIQUE (username)
);

-- ==================================================
-- Table: customer
-- Purpose: Store payment customers.
-- ==================================================
CREATE TABLE customer (
    id UUID NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_customer PRIMARY KEY (id)
);

-- ==================================================
-- Table: merchant
-- Purpose: Store merchants using the payment gateway.
-- ==================================================
CREATE TABLE merchant (
    id UUID NOT NULL,

    CONSTRAINT pk_merchant PRIMARY KEY (id)
);

-- ==================================================
-- Table: payment_intent
-- Purpose: Store payment intents before payment execution.
-- ==================================================
CREATE TABLE payment_intent (
                                id UUID NOT NULL,
                                amount NUMERIC(19,4) NOT NULL,
                                currency VARCHAR(10) NOT NULL,
                                status VARCHAR(50) NOT NULL,
                                description TEXT,

                                customer_id UUID,
                                merchant_id UUID,
                                payment_id UUID,

                                created_at TIMESTAMP WITH TIME ZONE,
                                updated_at TIMESTAMP WITH TIME ZONE,

                                CONSTRAINT pk_payment_intent PRIMARY KEY (id),

                                CONSTRAINT fk_payment_intent_customer
                                FOREIGN KEY (customer_id)
                                REFERENCES customer (id),

                                CONSTRAINT fk_payment_intent_merchant
                                FOREIGN KEY (merchant_id)
                                REFERENCES merchant (id)
);

-- ==================================================
-- Table: payment
-- Purpose: Store executed payment transactions.
-- ==================================================
CREATE TABLE payment (
                         id UUID NOT NULL,
                         amount NUMERIC(19,4) NOT NULL,
                         currency VARCHAR(10) NOT NULL,
                         description TEXT,
                         status VARCHAR(50) NOT NULL,

                         created_at TIMESTAMP WITH TIME ZONE,
                         updated_at TIMESTAMP WITH TIME ZONE,

                         provider_payment_id VARCHAR(255),
                         authorization_code VARCHAR(255),
                         acquirer_reference VARCHAR(255),
                         network_reference VARCHAR(255),

                         idempotency_key VARCHAR(255) NOT NULL,

                         merchant_id UUID,
                         customer_id UUID,

                         CONSTRAINT pk_payment PRIMARY KEY (id),

                        CONSTRAINT uk_payment_idempotency_key
                        UNIQUE (idempotency_key),

                        CONSTRAINT fk_payment_customer
                        FOREIGN KEY (customer_id)
                        REFERENCES customer (id),

                        CONSTRAINT fk_payment_merchant
                        FOREIGN KEY (merchant_id)
                        REFERENCES merchant (id)
);

-- ==================================================
-- Table: ledger
-- Purpose: Store financial ledger entries.
-- ==================================================
CREATE TABLE ledger (
                        id UUID NOT NULL,
                        amount NUMERIC(19,4) NOT NULL,
                        currency VARCHAR(10) NOT NULL,
                        description TEXT,

                        customer_id UUID,

                        CONSTRAINT pk_ledger PRIMARY KEY (id),

                        CONSTRAINT fk_ledger_customer
                            FOREIGN KEY (customer_id)
                                REFERENCES customer (id)
);

-- ==================================================
-- Table: audit_event
-- Purpose: Store audit events.
-- ==================================================
CREATE TABLE audit_event (
    id UUID NOT NULL,
    description TEXT NOT NULL,
    event_type VARCHAR(255),
    entity_type VARCHAR(50),
    entity_id UUID,
    actor_id UUID,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_audit_event PRIMARY KEY (id)
);

-- ==================================================
-- Table: webhook_event
-- Purpose: Store incoming webhook events.
-- ==================================================
CREATE TABLE webhook_event (
                               id UUID NOT NULL,

                               CONSTRAINT pk_webhook_event PRIMARY KEY (id)
);