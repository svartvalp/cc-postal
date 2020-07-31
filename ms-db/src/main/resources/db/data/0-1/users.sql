--liquibase formatted sql

--changeset mtugarev:db/data/0-1/users-1

INSERT INTO users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('users_seq'), 'Miksa', 'MiksaMiddle', 'MiksaLast', 'miksa', '4514000000', 'qwerty');
INSERT INTO users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('users_seq'), 'Mystery', 'MysteryMiddle', 'MysteryLast', 'Mystery', '4514000000', 'qwerty');
INSERT INTO users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('users_seq'), 'Deadsen', 'DeadsenMiddle', 'DeadsenLast', 'Deadsen', '4514000000', 'qwerty');
INSERT INTO users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('users_seq'), 'Shaman', 'ShamanMiddle', 'ShamanLast', 'Shaman', '4514000000', 'qwerty');