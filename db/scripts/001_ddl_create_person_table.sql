CREATE TABLE IF NOT EXISTS person
(
    id   SERIAL PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

comment on table person is 'Пользователь';
comment on column person.id is 'Идентификатор пользователя';
comment on column person.login is 'Логин пользователя';
comment on column person.password is 'Пароль пользователя';