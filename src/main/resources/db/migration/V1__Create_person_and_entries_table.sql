create table Person
(
    id   integer primary key,
    name varchar(255) not null UNIQUE
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

