create schema if not exists notification;

create table if not exists notification.emails
(
    id              uuid primary key,
    receiver        varchar(255) not null,
    subject         varchar(130) not null,
    content         text not null,
    status          varchar(10) not null,
    creation_time   timestamp    not null,
    update_time     timestamp
);
