CREATE TABLE loader
(
    "id"              serial       NOT NULL,
    "identity_number" VARCHAR(255) NOT NULL UNIQUE,
    "status"          VARCHAR(255) NOT NULL,
    CONSTRAINT LOADER_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE gate
(
    "id"     serial       NOT NULL,
    "name"   VARCHAR(255) NOT NULL UNIQUE,
    "status" varchar(255) NOT NULL,
    CONSTRAINT GATE_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE vehicle
(
    "id"                  serial       NOT NULL,
    "registration_number" VARCHAR(255) NOT NULL UNIQUE,
    "model"               VARCHAR(255) NOT NULL,
    "status"              VARCHAR(255) NOT NULL,
    "pallets_capacity"    integer      NOT NULL,
    CONSTRAINT VEHICLE_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE route
(
    "id"             serial       NOT NULL,
    "direction"      VARCHAR(50)  NOT NULL,
    "arrival_time"   timestamp    NOT NULL,
    "departure_time" timestamp,
    "status"         VARCHAR(255) NOT NULL,
    "vehicle_id"     integer,
    "gate_id"        integer,
    "pallet_count"   integer,
    CONSTRAINT ROUTE_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE pallet
(
    "id"                 serial       NOT NULL,
    "barcode"            VARCHAR(255) NOT NULL UNIQUE,
    "status"             VARCHAR(255) NOT NULL,
    "route_id"           integer      NOT NULL,
    "location_id"        integer,
    "loader_id"          integer,
    "status_update_date" timestamp    NOT NULL,
    CONSTRAINT PALLET_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE location
(
    "id"          serial       NOT NULL,
    "name"        VARCHAR(255) NOT NULL UNIQUE,
    "status"      VARCHAR(255) NOT NULL,
    "zone_number" integer      NOT NULL,
    "gate_id"     integer,
    CONSTRAINT LOCATION_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );



CREATE TABLE task
(
    "id"                      serial       NOT NULL,
    "pallet_id"               integer      NOT NULL,
    "departure_location_id"   integer      NOT NULL,
    "destination_location_id" integer,
    "loader_id"               integer      NOT NULL,
    "status"                  VARCHAR(255) NOT NULL,
    "status_update_date"      timestamp    NOT NULL,
    CONSTRAINT TASK_pk PRIMARY KEY ("id")
) WITH (
      OIDS= FALSE
    );




