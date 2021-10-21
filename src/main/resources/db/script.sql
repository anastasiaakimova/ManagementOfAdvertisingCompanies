CREATE DATABASE "AdvertisingCompanies";

CREATE TABLE public.usr
(
    "usrid"    uuid NOT NULL,
    name       character varying,
    mail       character varying,
    password   character varying,
    "role"     character varying,
    "isactive" boolean,
    PRIMARY KEY ("usrid")
);

CREATE TABLE public.advertiser
(
    "advertiserid" uuid NOT NULL,
    name           character varying,
    "description"  character varying,
    "isactive"     boolean,
    "usr"          uuid,
    PRIMARY KEY ("advertiserid"),
    CONSTRAINT usr FOREIGN KEY (usr)
        REFERENCES public.usr ("usrid") MATCH SIMPLE
);
CREATE TABLE IF NOT EXISTS public.campaign
(
    "campaignid"  uuid NOT NULL,
    name          character varying,
    "link"        character varying,
    country       character varying,
    language      character varying,
    age           character varying,
    "geolocation" character varying,
    "isactive"    boolean,
    advertiser    uuid,

    PRIMARY KEY ("campaignid"),
    CONSTRAINT advertiser FOREIGN KEY (advertiser)
        REFERENCES public.advertiser ("advertiserid") MATCH SIMPLE

);