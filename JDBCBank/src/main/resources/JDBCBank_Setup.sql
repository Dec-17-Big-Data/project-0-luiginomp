----------------------------------------------------------------TABLES
CREATE TABLE bank_user (
    user_id NUMBER(10) PRIMARY KEY,
    user_name VARCHAR(255) UNIQUE NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    account_id NUMBER(10)
);

CREATE TABLE bank_account (
    account_id NUMBER(10) PRIMARY KEY,
    account_balance DECIMAL DEFAULT '0',
    transaction_id NUMBER(10) DEFAULT '0'
);

CREATE TABLE account_transaction (
    transaction_date DATE
);

----------------------------------------------------------------CONSTRAINTS
ALTER TABLE BANK_USER
    ADD CONSTRAINT user_to_account_foreign_key
        FOREIGN KEY (account_id)
        REFERENCES bank_account (account_id)
        ON DELETE CASCADE;

----------------------------------------------------------------SEQUENCES
CREATE SEQUENCE user_id_sequence
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1
    CACHE 20;
    
CREATE SEQUENCE account_id_sequence
    MINVALUE 1
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

----------------------------------------------------------------STORED PROCEDURES
CREATE OR REPLACE PROCEDURE insert_user
    (new_name IN VARCHAR2, new_password IN VARCHAR2) AS
BEGIN
    INSERT INTO bank_user
        VALUES (user_id_sequence.NEXTVAL, new_name, new_password, NULL);
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE delete_user
    (input_name IN VARCHAR2) AS
BEGIN
    DELETE FROM bank_user WHERE input_name = user_name;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE delete_all_users AS
BEGIN
    DELETE FROM bank_user;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE update_username
    (current_name VARCHAR2, new_name VARCHAR2) AS
BEGIN
    UPDATE bank_user
        SET user_name = new_name
        WHERE user_name = current_name;
END;
/

CREATE OR REPLACE PROCEDURE update_password
    (input_name VARCHAR2, new_password VARCHAR2) AS
BEGIN
    UPDATE bank_user
        SET user_password = new_password
        WHERE user_name = input_name;
END;
/

CREATE OR REPLACE PROCEDURE insert_account
    (output_id OUT NUMBER) AS
BEGIN
    INSERT INTO bank_account
        VALUES (account_id_sequence.NEXTVAL,0, NULL);
    output_id := account_id_sequence.CURRVAL;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE delete_account
    (input_id IN NUMBER) AS
BEGIN
    DELETE FROM bank_account WHERE input_id = account_id;
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE deposit_balance
    (input_account_id IN NUMBER, input_amount IN DECIMAL) AS
BEGIN
    UPDATE bank_account
        SET account_balance = account_balance + input_amount
        WHERE account_id = input_account_id;
END;
/

CREATE OR REPLACE PROCEDURE withdraw_balance
    (input_account_id IN NUMBER, input_amount IN DECIMAL) AS
BEGIN
    UPDATE bank_account
        SET account_balance = account_balance - input_amount
        WHERE account_id = input_account_id;
END;
/

commit;