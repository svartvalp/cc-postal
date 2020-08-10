--liquibase formatted sql
--changeset mtugarev:db/object/0-2/users-1
ALTER TABLE users ADD COLUMN address_id bigint;

comment on column postal_schema.users.address_id IS 'адрес пользователя';
