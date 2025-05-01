CREATE TABLE accounts (
    account_id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES userTable(user_id)ON DELETE CASCADE ON UPDATE CASCADE,
    balance NUMERIC(15, 2) NOT NULL,
    account_name VARCHAR(64) NOT NULL,
    bank_name VARCHAR(64) NOT NULL,
    counter SMALLINT NOT NULL
);