--liquibase formatted sql

--changeset mtugarev:db/object/0-1/address-1
create table crashcourse.address (
    id bigint primary key,
    longitude varchar(100),
    latitude varchar(100),
    address varchar(100)
);

CREATE SEQUENCE crashcourse.address_seq START 1;

comment on table crashcourse.address IS 'таблица с адресами';
comment on column crashcourse.address.latitude IS 'широта';
comment on column crashcourse.address.longitude IS 'долгота';
comment on column crashcourse.address.address IS 'адрес в формате для Navigation Service';


