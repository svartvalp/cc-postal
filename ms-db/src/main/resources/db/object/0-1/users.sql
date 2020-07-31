--liquibase formatted sql

--changeset mtugarev:db/object/0-1/users-1
create table users
(
    id int primary key,
    first_name varchar(50),
    middle_name varchar(50),
    last_name varchar(50),
    login varchar(50) unique,
    passport_number varchar(15),
    password varchar(100)
);

CREATE SEQUENCE users_seq START 1;

COMMENT ON TABLE users IS 'Таблица с пользователями';
COMMENT ON COLUMN users.first_name IS 'Имя пользователя';
COMMENT ON COLUMN users.middle_name IS 'Отчество пользователя';
COMMENT ON COLUMN users.last_name IS 'Фамилия пользователя';
COMMENT ON COLUMN users.login IS 'Логин пользователя';
COMMENT ON COLUMN users.password IS 'Пароль пользователя зашифрованный bcrypt';
COMMENT ON COLUMN users.passport_number IS 'Номер пасспорта'