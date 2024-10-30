create table user (
    id bigint auto_increment primary key ,
    username varchar(10) not null,
    email varchar(30) not null unique,
    password varchar(255) not null,
    role enum ('ADMIN','USER') not null,
    created_at datetime(6),
    updated_at datetime(6)
);

create table schedule (
    id bigint auto_increment primary key ,
    date varchar(10) not null,
    title varchar(50) not null,
    contents varchar(255) not null,
    created_at datetime(6),
    updated_at datetime(6)
);

create table userSchedule (
    id bigint auto_increment primary key ,
    schedule_id bigint,
    user_id bigint
);

create table comment (
    id bigint auto_increment primary key ,
    schedule_id bigint,
    created_at datetime(6),
    updated_at datetime(6),
    comment varchar(100) not null
)

