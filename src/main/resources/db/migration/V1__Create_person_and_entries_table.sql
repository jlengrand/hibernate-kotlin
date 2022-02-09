create table Person
(
    id   integer primary key,
    name varchar(255) not null UNIQUE,
    created_at TIMESTAMP NOT NULL ON CONFLICT REPLACE DEFAULT current_timestamp, -- because hibernate sends nulls
    updated_at TIMESTAMP NOT NULL ON CONFLICT REPLACE DEFAULT current_timestamp -- because hibernate sends nulls
);

create table Entry
(
    id   integer primary key,
    data varchar(255) not null
);

create table Person_Entry
(
    Person_id  integer not null,
    entries_id integer not null unique
);

CREATE TRIGGER [UpdateLastTimePerson]
    AFTER UPDATE
     ON Person
     FOR EACH ROW
BEGIN
UPDATE Person SET updated_at = current_timestamp WHERE id = old.id;
END;