CREATE TABLE IF NOT EXISTS "user"
(
    username VARCHAR(45) PRIMARY KEY NOT NULL,
    password TEXT                    NOT NULL
);

CREATE TABLE IF NOT EXISTS otp
(
    username VARCHAR(45) PRIMARY KEY NOT NULL,
    code     VARCHAR(45)             NULL
);
