--liquibase formatted sql

--changeset mtugarev:db/object/0-1/departure-1
CREATE TABLE postal_schema.departure (
    id bigint primary key,
    user_id bigint,
    departure_point_id bigint,
    arriving_point_id bigint,
    type VARCHAR(255),
    departure_date timestamp,
    arrived boolean
);

CREATE SEQUENCE postal_schema.departure_seq START WITH 1;

COMMENT ON SEQUENCE postal_schema.departure_seq IS 'id посылок';
COMMENT ON TABLE postal_schema.departure IS 'Данные посылки';
COMMENT ON COLUMN postal_schema.departure.id IS 'id посылок';
COMMENT ON COLUMN postal_schema.departure.user_id IS 'id отправителя';
COMMENT ON COLUMN postal_schema.departure.departure_point_id IS 'id точки отправления';
COMMENT ON COLUMN postal_schema.departure.arriving_point_id IS 'id точки прибытия';
COMMENT ON COLUMN postal_schema.departure.type IS 'тип посылки';
COMMENT ON COLUMN postal_schema.departure.departure_date IS 'дата отправления';
COMMENT ON COLUMN postal_schema.departure.arrived IS 'статус посылки';