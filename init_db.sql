drop database fanyacode;
drop user postgres;
create user postgres with password 'postgres';
create database fanyacode with template=template0 owner=postgres;
\connect fanyacode;
alter default privileges grant all on tables to postgres;
alter default privileges grant all on sequences to postgres;

create table users(
                         user_id integer primary key not null,
                         first_name varchar(20) not null,
                         last_name varchar(20) not null,
                         email varchar(30) not null,
                         password text not null,
                         enabled boolean not null
);

create table categories(
                              category_id integer primary key not null,
                              user_id integer not null,
                              title varchar(20) not null,
                              description varchar(50) not null
);
alter table categories add constraint cat_users_fk
    foreign key (user_id) references users(user_id);

create table posts(
                                post_id integer primary key not null,
                                category_id integer not null,
                                user_id integer not null,
                                amount numeric(10,2) not null,
                                note varchar(50) not null,
                                post_date bigint not null
);
alter table posts add constraint posts_cat_fk
    foreign key (category_id) references categories(category_id);
alter table posts add constraint posts_users_fk
    foreign key (user_id) references users(user_id);

create table authorities(
                      authority_id integer primary key not null,
                      user_id integer not null,
                      authority varchar(10) not null

);
alter table authorities add constraint authorities_users_fk
    foreign key (user_id) references users(user_id);

create sequence users_seq increment 1 start 1;
create sequence categories_seq increment 1 start 1;
create sequence posts_seq increment 1 start 1000;
create sequence authorities_seq increment 1 start 1;

create unique index ix_auth_userid on authorities (user_id,authority);
