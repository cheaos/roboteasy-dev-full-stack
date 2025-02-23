CREATE TABLE RBTUSER (
    id SERIAL PRIMARY KEY,
    cdfuncionario INT NOT NULL,
    cdcracha INT NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20),
    dsnome VARCHAR(100),
    instatus INT
);