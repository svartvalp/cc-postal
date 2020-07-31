--liquibase formatted sql

--changeset mtugarev:db/object/0-1/user_addresses-1
create table crashcourse.user_addresses (
    user_id bigint,
    address_id bigint
);

comment on table crashcourse.user_addresses IS 'многое ко многим пользователь-адреса';
comment on column crashcourse.user_addresses.user_id IS 'id пользователя';
comment on column crashcourse.user_addresses.address_id IS 'id адреса';


