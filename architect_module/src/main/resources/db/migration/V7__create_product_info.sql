CREATE TABLE IF NOT EXISTS product_info (
                                                id varchar PRIMARY KEY,
                                                description varchar,
                                                amount_of_discount int4
);
INSERT INTO product_info (id, description, amount_of_discount)
vALUES('для местных', 'скидка для тех, кто бронирует в своем домашнем регионе', 10);