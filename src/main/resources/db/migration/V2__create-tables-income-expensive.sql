CREATE TABLE income (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        amount DECIMAL(19, 2) NOT NULL,
                        date DATE NOT NULL,
                        category VARCHAR(50) NOT NULL
);

CREATE TABLE expense (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         description TEXT,
                         amount DECIMAL(19, 2) NOT NULL,
                         date DATE NOT NULL,
                         category VARCHAR(50) NOT NULL
);