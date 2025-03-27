create schema if not exists test;
create table if not exists test.users
(
    id           uuid         not null
    constraint users_pk
    primary key,
    name         varchar(128) not null,
    email        varchar(128),
    age          integer      not null,
    weight       integer      not null,
    height       integer      not null,
    target       varchar      not null,
    daily_target integer      not null
    );

create table if not exists test.meals
(
    id            uuid         not null
    constraint meals_pk
    primary key,
    name          varchar(128) not null,
    calories      integer      not null,
    proteins      integer      not null,
    fats          integer      not null,
    carbohydrates integer      not null
    );

create table if not exists test.meals_history
(
    id          uuid      not null
    constraint meals_history_pk
    primary key,
    user_id     uuid      not null
    constraint meals_history_users_fk
    references test.users,
    meal_id     uuid      not null
    constraint meals_history_meals_fk
    references test.meals,
    meal_amount integer   not null,
    date_time   timestamp not null
);

