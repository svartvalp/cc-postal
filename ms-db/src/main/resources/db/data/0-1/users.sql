--liquibase formatted sql

--changeset mtugarev:db/data/0-1/users-1

INSERT INTO crashcourse.users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('crashcourse.users_seq'), 'Miksa', 'MiksaMiddle', 'MiksaLast', 'miksa', '4514000000', '$2y$12$mNy5tseWEBXj.vdH35/4welTjtoEcsXUZo2JfhEPO72bAiGLYtbr2');
INSERT INTO crashcourse.users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('crashcourse.users_seq'), 'Mystery', 'MysteryMiddle', 'MysteryLast', 'Mystery', '4514000000', '$2y$12$mNy5tseWEBXj.vdH35/4welTjtoEcsXUZo2JfhEPO72bAiGLYtbr2');
INSERT INTO crashcourse.users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('crashcourse.users_seq'), 'Deadsen', 'DeadsenMiddle', 'DeadsenLast', 'Deadsen', '4514000000', '$2y$12$mNy5tseWEBXj.vdH35/4welTjtoEcsXUZo2JfhEPO72bAiGLYtbr2');
INSERT INTO crashcourse.users (id, first_name, middle_name, last_name, login, passport_number, password)
                    VALUES (nextval('crashcourse.users_seq'), 'Shaman', 'ShamanMiddle', 'ShamanLast', 'Shaman', '4514000000', '$2y$12$mNy5tseWEBXj.vdH35/4welTjtoEcsXUZo2JfhEPO72bAiGLYtbr2');