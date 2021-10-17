CREATE TABLE IF NOT EXISTS public.usr
(
    "id"    uuid NOT NULL,
    name   character varying,
    mail       character varying,
    password   character varying,
    "role"     character varying,
    "isactive" boolean,
    PRIMARY KEY ("id")
    );
-- password - admin
INSERT INTO usr("id", name, mail, password, role, "isactive")
VALUES ('f27b14d2-24fc-11ec-9621-0242ac130002', 'Alex', 'asdf@gmail.com',
        '$2a$12$GKnqF.p2f2iiw1WXNVWVuO6VmjgYy3dtSReAA3KgrQKH7v44YzB4a', 'ADMIN', true);

-- password - user
INSERT INTO usr("id", name, mail, password, role, "isactive")
VALUES ('416bee5a-2501-11ec-9621-0242ac130002', 'Mary', 'qwert@gmail.com',
        '$2a$12$ukH5EN3vizXrqWFTZ1enQuOLljSA5IgVFafiuOe/knVZ8qxoOg5uG', 'USER', true);