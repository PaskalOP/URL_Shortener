create table urls(
ID bigint primary key not null,
originURL varchar not null,
shortURL varchar not null,
countUse bigint,
UserID UUID,
creatingDate Date,
finishDate DATE);
