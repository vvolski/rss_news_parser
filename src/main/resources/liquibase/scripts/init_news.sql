-- changeset vvolski:3
create table news
(
    id       bigint not null constraint news_pk primary key,
    title    text not null,
    guid     text unique not null,
    link     text not null,
    pub_date timestamp not null
);

create index "news_pub_date" on news (pub_date);

create sequence news_sequence as bigint;