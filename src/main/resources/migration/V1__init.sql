drop schema if exists app cascade;
create schema if not exists app;

set schema 'app';

-- GRANT USAGE ON SCHEMA schema_name TO username;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA schema_name TO username;

create sequence if not exists hibernate_sequence
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table role
(
    id text not null,
    description text not null,
    name text not null
);
alter table role add constraint pk_role primary key (id);

insert into role (id,name, description) values ('SYSTEM_ADMIN','System Admin', 'The is the root access role. Has access to everything!');
insert into role (id,name, description) values ('COMPANY_ADMIN','Company Admin', 'The role reserved to say a user has access to company information and access to change sensitive company information');
insert into role (id,name, description) values ('ACCOUNTANT','Accounting User', 'The role reserved to say a user has access to accounting information.');
insert into role (id,name, description) values ('DRIVER','Driver User', 'The role reserved to say a user who is a driver.');

-- =================================================================================================
-- USER related tables
-- =================================================================================================
create sequence if not exists users_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table users
(
    id integer default nextval('users_id_seq'),
    username text not null,
    password text not null,
    first_name text null,
    last_name text null,
    display_name text null,
    email text not null,
    mobile_no text null,
    activation_serial_number text null,
    profile_image_uri text null,
    deactivated_date timestamptz,
    user_deactivate_reason_id integer,
    -- mutable entity attributes --
    created_by integer,
    created_on timestamptz not null default now()
);
alter sequence users_id_seq owned by users.id;
alter table users add constraint pk_users primary key (id);
create unique index ux_users_001 on users (username);
create unique index ux_users_email on users (email);

create sequence if not exists user_role_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;

create table user_role
(
    id integer default nextval('user_role_id_seq'),
    user_id integer not null,
    role_id text not null,
    -- mutable entity attributes --
    created_by integer references users,
    created_on timestamptz not null default now()
);
alter sequence user_role_id_seq owned by user_role.id;
alter table user_role add constraint pk_user_role primary key (id);
alter table user_role add constraint fk_user_role_user foreign key (user_id) references users;
alter table user_role add constraint fk_user_role_role foreign key (role_id) references role;
create unique index ux_user_role_001 on user_role (user_id, role_id);
