CREATE TABLE IF NOT EXISTS token_integration (
                                     id int8 PRIMARY KEY,
                                     service_name varchar,
                                     token varchar
);

CREATE SEQUENCE token_sequence START 1 INCREMENT 1;