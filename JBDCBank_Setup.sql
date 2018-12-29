create table bank_user 
(user_id number (10) primary key, username varchar2 (255), password varchar2 (255), account_id number (10));

create table bank_account (account_id number (10) primary key, balance binary_float default '0', user_id number (10));

alter table bank_user add constraint user_to_account_foreign_key foreign key (account_id) references bank_account (account_id) on delete cascade;

--Learn how to implement the following from Java
--1. Insert new row into tables
--2. Edit existing row in a table

insert into bank_user (user_id, username, password, account_id) values (0,'Overseer', 'Vault#101', null);

insert into bank_user (user_id, username, password, account_id) values (1,'LuiginoMP', 'C0d3L!k34B0$$', null);

insert into Bank_account (account_id, balance, user_id) values (0, 20.00, 1);