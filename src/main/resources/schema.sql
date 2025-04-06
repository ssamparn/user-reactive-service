CREATE TABLE IF NOT EXISTS users (
    id           UUID DEFAULT RANDOM_UUID(),
    first_name   VARCHAR(50) NOT NULL,
    last_name    VARCHAR(50) NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);