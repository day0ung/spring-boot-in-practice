CREATE TABLE IF NOT EXISTS users (
    id        VARCHAR(10)  PRIMARY KEY,
    name      VARCHAR(20)  NOT NULL,
    password  VARCHAR(20)  NOT NULL,
    level     INT          NOT NULL DEFAULT 1,
    login     INT          NOT NULL DEFAULT 0,
    recommend INT          NOT NULL DEFAULT 0,
    email     VARCHAR(50)
);
