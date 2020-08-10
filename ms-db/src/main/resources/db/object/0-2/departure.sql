--liquibase formatted sql

--changeset mtugarev:db/object/0-2/departure-1
ALTER TABLE departure ADD COLUMN nearest_user_id bigint;

comment on column postal_schema.departure.nearest_user_id IS 'id ближайшего к адресу доставки пользователя';