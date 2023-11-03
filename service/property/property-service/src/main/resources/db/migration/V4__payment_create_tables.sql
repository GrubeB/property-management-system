CREATE TABLE t_payment (
  payment_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   buyer_id UUID,
   seller_id UUID,
   is_payment_done BOOLEAN,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_payment PRIMARY KEY (payment_id)
);
CREATE TABLE t_payment_order (
  payment_order_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   payment_name VARCHAR(255) NOT NULL,
   amount DECIMAL NOT NULL,
   current VARCHAR(255) NOT NULL,
   status VARCHAR(255),
   domain_object_id UUID,
   domain_object_type VARCHAR(255),
   is_ledger_updated BOOLEAN NOT NULL,
   is_wallet_updated BOOLEAN NOT NULL,
   payment_entity_payment_id UUID NOT NULL,
   CONSTRAINT pk_t_payment_order PRIMARY KEY (payment_order_id)
);
CREATE TABLE t_ledger (
  ledger_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   account_id UUID NOT NULL,
   debit DECIMAL NOT NULL,
   credit DECIMAL NOT NULL,
   date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_ledger PRIMARY KEY (ledger_id)
);
CREATE TABLE t_wallet (
  wallet_id UUID NOT NULL,
   created_by VARCHAR(255) NOT NULL,
   created_datetime TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   last_modified_by VARCHAR(255),
   last_modified_datetime TIMESTAMP WITHOUT TIME ZONE,
   account_id UUID NOT NULL,
   amount DECIMAL NOT NULL,
   property_id UUID NOT NULL,
   CONSTRAINT pk_t_wallet PRIMARY KEY (wallet_id)
);

ALTER TABLE t_wallet ADD CONSTRAINT uc_walletentity_account_id UNIQUE (account_id);
ALTER TABLE t_wallet ADD CONSTRAINT FK_T_WALLET_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);
ALTER TABLE t_ledger ADD CONSTRAINT FK_T_LEDGER_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);
ALTER TABLE t_payment_order ADD CONSTRAINT FK_T_PAYMENT_ORDER_ON_PAYMENT_ENTITY_PAYMENT FOREIGN KEY (payment_entity_payment_id) REFERENCES t_payment (payment_id);
ALTER TABLE t_payment ADD CONSTRAINT FK_T_PAYMENT_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES t_property (property_id);