CREATE TABLE vehicle
(
    id bigserial PRIMARY KEY,
    mark character varying(20) NOT NULL,
    model character varying(20) NOT NULL,
    type character varying(20) NOT NULL,
    mileage integer NOT NULL,
    price integer NOT NULL,
    id_profile bigint NOT NULL,

    CONSTRAINT fk_vehicle_to_profile FOREIGN KEY (id_profile)
        REFERENCES profile (id)
);