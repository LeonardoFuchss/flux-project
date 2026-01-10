ALTER TABLE income ADD COLUMN user_id BIGINT;

ALTER TABLE income ADD CONSTRAINT fk_income_user_id
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE expense ADD COLUMN user_id BIGINT;

ALTER TABLE expense ADD CONSTRAINT fk_expense_user_id
    FOREIGN KEY (user_id) REFERENCES users(id);