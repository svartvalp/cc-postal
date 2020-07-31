--liquibase formatted sql

--changeset mtugarev:db/object/0-1/departure-1
CREATE TABLE crashcourse.departure (
    id bigint primary key,
    user_id bigint,
    departure_point_id bigint,
    arriving_point_id bigint,
    type VARCHAR(255),
    departure_date timestamp,
    arrived boolean
);

CREATE SEQUENCE crashcourse.departure_seq START WITH 1;

COMMENT ON SEQUENCE crashcourse.departure_seq IS 'id посылок';
COMMENT ON TABLE crashcourse.departure IS 'Данные посылки';
COMMENT ON COLUMN crashcourse.departure.id IS 'id посылок';
COMMENT ON COLUMN crashcourse.departure.user_id IS 'id отправителя';
COMMENT ON COLUMN crashcourse.departure.departure_point_id IS 'id точки отправления';
COMMENT ON COLUMN crashcourse.departure.arriving_point_id IS 'id точки прибытия';
COMMENT ON COLUMN crashcourse.departure.type IS 'тип посылки';
COMMENT ON COLUMN crashcourse.departure.departure_date IS 'дата отправления';
COMMENT ON COLUMN crashcourse.departure.arrived IS 'статус посылки';