
create table bank_user (user_id number (10) primary key, user_name varchar2 (255)unique not null, user_password varchar2 (255) not null, account_id number (10));

create table bank_account (account_id number (10) primary key, account_balance binary_float default '0', user_id number (10) unique not null);

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
    (user_id in number) as 
begin
    insert into bank_account values (account_id_sequence.nextval, 0, user_id);
    commit;
end;
/

create or replace procedure delete_user
    (user_name in varchar2) as
begin
    delete from bank_user where bank_user.user_name = user_name;
end;
/

commit;

--DELETE BELOW LINE PRIOR TO FINALIZING

call insert_user('LeChiffre', 'baccarat');