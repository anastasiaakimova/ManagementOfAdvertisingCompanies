CREATE TABLE IF NOT EXISTS public.advertiser
(
    "id" uuid NOT NULL,
    name           character varying,
    "description"  character varying,
    "isactive"     boolean,
    "usr"          uuid,
    PRIMARY KEY ("id"),
    CONSTRAINT usr FOREIGN KEY (usr)
        REFERENCES public.usr ("id") MATCH SIMPLE
);

INSERT INTO advertiser("id", name, description, "isactive" , usr)
VALUES ('a3a9fd9e-25ee-11ec-9621-0242ac130002','one','one desk', true,'f27b14d2-24fc-11ec-9621-0242ac130002');


INSERT INTO advertiser("id", name, description, "isactive" , usr)
VALUES ('c8de2608-25ee-11ec-9621-0242ac130002','two','two desk', true,'416bee5a-2501-11ec-9621-0242ac130002');