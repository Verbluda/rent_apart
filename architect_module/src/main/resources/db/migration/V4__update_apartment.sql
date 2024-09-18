CREATE TABLE IF NOT EXISTS photo (
                                       id int8 PRIMARY KEY,
                                       photo_of_apartment bytea
);

CREATE SEQUENCE photo_sequence START 2 INCREMENT 1;

ALTER TABLE apartment
ADD photo_id int8 REFERENCES photo(id);