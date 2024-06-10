create table urls(
ID bigint primary key not null,
originURL varchar not null,
shortURL varchar not null,
countUse bigint,
login varchar,
creatingDate Date,
finishDate DATE);
