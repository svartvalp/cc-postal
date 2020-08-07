--liquibase formatted sql

--changeset mtugarev:db/object/0-1/address-1
create table postal_schema.address (
    id bigint primary key,
    longitude double precision,
    latitude double precision,
    address varchar(100) unique
);

CREATE SEQUENCE postal_schema.address_seq START 1;

comment on table postal_schema.address IS 'таблица с адресами';
comment on column postal_schema.address.latitude IS 'широта';
comment on column postal_schema.address.longitude IS 'долгота';
comment on column postal_schema.address.address IS 'адрес';


