INSERT INTO ACCOUNTS (id, balance) VALUES (1, 10);
INSERT INTO ACCOUNTS (id, balance) VALUES (2, 0);

INSERT INTO USERS (email, password, username, account_id) VALUES ('present@mail.com', '$2a$10$UNqJhTQaA4ft1alK8WO09.r3PLd0JfohVWbmU9TPXNE945xEIzaqK', 'present', 1);
INSERT INTO USERS (email, password, username, account_id) VALUES ('example@mail.com', '$2a$10$wbRxKY2EiDGt.RFfZahQX.HAj0.uf0021grj4MXgjEQ2nzqoaCmhu', 'example', 2);

INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend1');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend2');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend3');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend4');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'example');


--INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES (1, 10, "SEND_MONEY", "example@mail.com", "present@mail.com", "2023/10/20", "Cinema bill");