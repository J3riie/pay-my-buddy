INSERT INTO ACCOUNTS (balance) VALUES (100);
INSERT INTO USERS (email, password, username, account_id) VALUES ('robin.hugues@mail.com', '$2a$10$UNqJhTQaA4ft1alK8WO09.r3PLd0JfohVWbmU9TPXNE945xEIzaqK', 'robinhugues', SELECT MAX(ID) FROM ACCOUNTS);

INSERT INTO ACCOUNTS (balance) VALUES (30);
INSERT INTO USERS (email, password, username, account_id) VALUES ('tony.hawk@mail.com', '$2a$10$d0M98MZB1qRbs7LGD7QPI.05rZdrdfCTweYsmoM1.QsF8NuQd2Rly', 'tony', SELECT MAX(ID) FROM ACCOUNTS);

INSERT INTO ACCOUNTS (balance) VALUES (30);
INSERT INTO USERS (email, password, username, account_id) VALUES ('martin@mail.com', '$2a$10$d0M98MZB1qRbs7LGD7QPI.05rZdrdfCTweYsmoM1.QsF8NuQd2Rly', 'martin', SELECT MAX(ID) FROM ACCOUNTS);

INSERT INTO ACCOUNTS (balance) VALUES (30);
INSERT INTO USERS (email, password, username, account_id) VALUES ('anothermartin@mail.com', '$2a$10$d0M98MZB1qRbs7LGD7QPI.05rZdrdfCTweYsmoM1.QsF8NuQd2Rly', 'martin le second', SELECT MAX(ID) FROM ACCOUNTS);

INSERT INTO CONNECTIONS (email, connection) VALUES ('robin.hugues@mail.com', 'tony');
INSERT INTO CONNECTIONS (email, connection) VALUES ('tony.hawk@mail.com', 'robinhugues');

INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES ('1234-1234-1234', 10, 'SEND_MONEY', 'robinhugues', 'tony', '2023-10-25 00:00:00', 'Cinema bill');
INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES ('1234-1234-1235', 45, 'SEND_MONEY', 'tony', 'robinhugues', '2023-10-24 00:00:00', 'Restaurant bill');
INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES ('1234-1234-1236', 20, 'SEND_MONEY', 'robinhugues', 'tony', '2023-10-23 00:00:00', 'Supermarket');
INSERT INTO TRANSACTIONS (id, amount, transaction_type, receiver, sender, created, description) VALUES ('1234-1234-1237', 14, 'SEND_MONEY', 'robinhugues', 'tony', '2023-10-22 00:00:00', 'Pizzas');