ALTER TABLE transaction ADD COLUMN user_id BIGINT;

ALTER TABLE transaction ADD CONSTRAINT fk_transaction_user_id
    FOREIGN KEY (user_id) REFERENCES users(id);