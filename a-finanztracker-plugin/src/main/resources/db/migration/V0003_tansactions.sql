CREATE TABLE transactions (
    transaction_id SERIAL,
    bank_account_id UUID NOT NULL REFERENCES accounts(account_id)ON DELETE CASCADE ON UPDATE CASCADE, 
    amount DECIMAL(15, 2) NOT NULL,
    transaction_description TEXT, 
    execution_date DATE NOT NULL, 
    entry_date DATE NOT NUL DEFAULT CURRENT_DATE, 
    last_modified_date DATE NOT NULL DEFAULT CURRENT_DATE,
    counterparty counterparty_id NOT NULL REFERENCES counterparty(counterparty_id)ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (transaction_id, bank_account_id)
);
CREATE Table categories (
    category_name VARCHAR(64) PRIMARY KEY,
    category_description TEXT,
);
CREATE TABLE relation_transaction_category (
    transaction_id SERIAL NOT NULL REFERENCES transactions(transaction_id)ON DELETE CASCADE ON UPDATE CASCADE,
    bank_account_id UUID NOT NULL REFERENCES accounts(account_id)ON DELETE CASCADE ON UPDATE CASCADE, 
    category_name VARCHAR(64) NOT NULL REFERENCES categories(category_name)ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (transaction_id, bank_account_id, category_name)
);
CREATE Table counterparty (
    counterparty_id UUID PRIMARY KEY,
    counterparty_name VARCHAR(64) NOT NULL,
    counterparty_description TEXT,
);
CREATE TABLE reaccuring (
    reaccuring_id SERIAL,
    bank_account_id UUID NOT NULL REFERENCES accounts(account_id)ON DELETE CASCADE ON UPDATE CASCADE,
    reaccuring_name VARCHAR(64) NOT NULL,
    reaccuring_description TEXT,
    amount DECIMAL(15, 2) NOT NULL,
    reaccuring_start_date DATE NOT NULL,
    reaccuring_last_modified_date DATE NOT NULL DEFAULT CURRENT_DATE,
    reaccuring_end_date DATE,
    interval_in_days INT NOT NULL DEFAULT 28,
    PRIMARY KEY (reaccuring_id, bank_account_id)
);
CREATE TABLE relation_reaccuring_category (
    reaccuring_id SERIAL NOT NULL REFERENCES reaccuring(reaccuring_id) ON DELETE CASCADE ON UPDATE CASCADE,
    bank_account_id UUID NOT NULL REFERENCES accounts(account_id) ON DELETE CASCADE ON UPDATE CASCADE,
    category_name VARCHAR(64) NOT NULL REFERENCES categories(category_name) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (reaccuring_id, bank_account_id, category_name)
);
