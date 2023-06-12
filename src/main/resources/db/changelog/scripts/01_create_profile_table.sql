CREATE TABLE profile
(
    id bigserial PRIMARY KEY,
    login character varying(20) UNIQUE NOT NULL,
    password text NOT NULL
);