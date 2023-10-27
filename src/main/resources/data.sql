INSERT INTO users (email, password, username) VALUES ('present@mail.com', '$2a$10$UNqJhTQaA4ft1alK8WO09.r3PLd0JfohVWbmU9TPXNE945xEIzaqK', 'present');
INSERT INTO users (email, password, username) VALUES ('example@mail.com', '$2a$10$wbRxKY2EiDGt.RFfZahQX.HAj0.uf0021grj4MXgjEQ2nzqoaCmhu', 'example');

INSERT INTO friends (user_email, friends) VALUES ('present@mail.com', 'friend1');
INSERT INTO friends (user_email, friends) VALUES ('present@mail.com', 'friend2');
INSERT INTO friends (user_email, friends) VALUES ('present@mail.com', 'friend3');
INSERT INTO friends (user_email, friends) VALUES ('present@mail.com', 'friend4');

--INSERT INTO transactions (idTransaction, amount, type, connection, email, date, description) VALUES (1, 10, "SEND_MONEY", "example@mail.com", "present@mail.com", "2023/10/20", "Cinema bill");