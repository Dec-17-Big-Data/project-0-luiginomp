
create table bank_user (user_id number (10) primary key, user_name varchar2 (255)unique not null, user_password varchar2 (255) not null, account_id number (10));

create table bank_account (account_id number (10) primary key, account_balance binary_float default '0', transaction_id number (10) default '0');

alter table bank_user add constraint user_to_account_foreign_key foreign key (account_id) references bank_account (account_id) on delete cascade;

create sequence user_id_sequence
    minvalue 1
    start with 1
    increment by 1
    cache 20;
    
create sequence account_id_sequence
    minvalue 1
    start with 1
    increment by 1
    cache 20;
  
create or replace procedure insert_user 
    (new_name in varchar2, new_password in varchar2) as 
begin
    insert into bank_user values (user_id_sequence.nextval, new_name, new_password, null);
    commit;
end;
/

create or replace procedure insert_account 
    (output_id out number) as
begin
    insert into bank_account values (account_id_sequence.nextval, 0, null);
    output_id := account_id_sequence.currval;
    commit;
end;
/

create or replace procedure delete_user
    (input_name in varchar2) as
begin
    delete from bank_user where input_name = bank_user.user_name;
    commit;
end;
/

create or replace procedure delete_all_users as
begin
    delete from bank_user;
    commit;
end;
/

create or replace procedure delete_account
    (input_id number) as
begin
    delete from bank_account where input_id = bank_account.account_id;
    commit;
end;
/

commit;