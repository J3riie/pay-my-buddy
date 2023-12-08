INSERT INTO ACCOUNTS (balance) VALUES (100);
-- INSERT INTO USERS (email, password, username, account_id) VALUES ('present@mail.com', '$2a$10$UNqJhTQaA4ft1alK8WO09.r3PLd0JfohVWbmU9TPXNE945xEIzaqK', 'present', SELECT MAX(ID) FROM ACCOUNTS);
INSERT INTO USERS (email, password, username, account_id) VALUES ('present@mail.com', '$2a$10$d0M98MZB1qRbs7LGD7QPI.05rZdrdfCTweYsmoM1.QsF8NuQd2Rly', 'present', SELECT MAX(ID) FROM ACCOUNTS);

INSERT INTO ACCOUNTS (balance) VALUES (30);
INSERT INTO USERS (email, password, username, account_id) VALUES ('example@mail.com', '$2a$10$d0M98MZB1qRbs7LGD7QPI.05rZdrdfCTweYsmoM1.QsF8NuQd2Rly', 'example', SELECT MAX(ID) FROM ACCOUNTS);

INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend1');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend2');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend3');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'friend4');
INSERT INTO CONNECTIONS (email, connection) VALUES ('present@mail.com', 'example');


--INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES (1, 10, "SEND_MONEY", "example@mail.com", "present@mail.com", "2023/10/20", "Cinema bill");