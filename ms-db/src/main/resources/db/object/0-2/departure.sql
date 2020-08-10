--liquibase formatted sql

--changeset mtugarev:db/object/0-2/departure-1
ALTER TABLE postal_schema.departure ADD COLUMN nearest_user_id bigint;
ALTER TABLE postal_schema.departure ADD COLUMN arriving_date timestamp;

comment on column postal_schema.departure.nearest_user_id IS 'id ближайшего к адресу доставки пользователя';
comment on column postal_schema.departure.arriving_date IS 'ожидаемая дата доставки';