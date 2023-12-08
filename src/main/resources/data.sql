INSERT INTO USERS (email, password, username) VALUES ('present@mail.com', '$2a$10$UNqJhTQaA4ft1alK8WO09.r3PLd0JfohVWbmU9TPXNE945xEIzaqK', 'present');
INSERT INTO USERS (email, password, username) VALUES ('example@mail.com', '$2a$10$wbRxKY2EiDGt.RFfZahQX.HAj0.uf0021grj4MXgjEQ2nzqoaCmhu', 'example');

INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend1');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend2');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend3');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend4');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'example');

INSERT INTO ACCOUNTS (balance, user_id) VALUES (10, 'present@mail.com');

--INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES (1, 10, "SEND_MONEY", "example@mail.com", "present@mail.com", "2023/10/20", "Cinema bill");