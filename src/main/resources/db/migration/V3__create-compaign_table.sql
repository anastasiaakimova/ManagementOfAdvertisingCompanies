CREATE TABLE IF NOT EXISTS public.campaign
(
    "id"  uuid NOT NULL,
    name          character varying,
    "link"        character varying,
    country       character varying,
    language      character varying,
    age           character varying,
    "geolocation" character varying,
    "isactive"    boolean,
    advertiser    uuid,

    PRIMARY KEY ("id"),
    CONSTRAINT advertiser FOREIGN KEY (advertiser)
        REFERENCES public.advertiser ("id") MATCH SIMPLE

);

INSERT INTO campaign("id", name, link, country , language, age, geolocation, "isactive", advertiser)
VALUES ('f8dba4a6-25f4-11ec-9621-0242ac130002','companyOne','www.companyOne.com',' {England, USA, Russia, Spain}', '{english, spanish, russian}', '20-70', '127.50.84, 90.40.190', true,  'a3a9fd9e-25ee-11ec-9621-0242ac130002');

INSERT INTO campaign("id", name, link, country , language, age, geolocation, "isactive", advertiser)
VALUES ('b4fbded4-25f6-11ec-9621-0242ac130002','companyTwo','www.companyTwo.com',' {England, USA}', '{english}', '50-80', '127.50.84, 90.40.190', true,  'c8de2608-25ee-11ec-9621-0242ac130002');