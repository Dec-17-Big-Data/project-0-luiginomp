
create table bank_user (user_id number (10) primary key, user_name varchar2 (255), user_password varchar2 (255), user_type varchar2 (255), account_id number (10));

create table bank_account (account_id number (10) primary key, account_balance binary_float default '0', user_id number (10));

alter table bank_user add constraint user_to_account_foreign_key foreign key (account_id) references bank_account (account_id) on delete cascade;

create sequence user_id_sequence
    minvalue 1
    start with 1
    increment by 1
    cache 20;
  
create or replace procedure insert_user 
    (new_name in varchar2, new_password in varchar2, new_type in varchar2) is 
begin
    insert into bank_user values (user_id_sequence.nextval, new_name, new_password, new_type, null);
    commit;
end;
/


commit;