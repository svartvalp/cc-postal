--liquibase formatted sql
--changeset mtugarev:db/object/0-1/users-1
create table postal_schema.users
(
    id bigint primary key,
    first_name varchar(50),
    middle_name varchar(50),
    last_name varchar(50),
    login varchar(50) unique,
    passport_number varchar(15),
    password varchar(100)
);

CREATE SEQUENCE postal_schema.users_seq START 1;

COMMENT ON TABLE postal_schema.users IS 'Таблица с пользователями';
COMMENT ON COLUMN postal_schema.users.first_name IS 'Имя пользователя';
COMMENT ON COLUMN postal_schema.users.middle_name IS 'Отчество пользователя';
COMMENT ON COLUMN postal_schema.users.last_name IS 'Фамилия пользователя';
COMMENT ON COLUMN postal_schema.users.login IS 'Логин пользователя';
COMMENT ON COLUMN postal_schema.users.password IS 'Пароль пользователя зашифрованный bcrypt';
COMMENT ON COLUMN postal_schema.users.passport_number IS 'Номер пасспорта'