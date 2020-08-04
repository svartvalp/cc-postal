--liquibase formatted sql

--changeset mtugarev:db/object/0-1/user_addresses-1
create table postal_schema.user_addresses (
    user_id bigint,
    address_id bigint
);

comment on table postal_schema.user_addresses IS 'многое ко многим пользователь-адреса';
comment on column postal_schema.user_addresses.user_id IS 'id пользователя';
comment on column postal_schema.user_addresses.address_id IS 'id адреса';


