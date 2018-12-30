create table bank_user (user_id number (10) primary key, username varchar2 (255), password varchar2 (255), account_id number (10));

create table bank_account (account_id number (10) primary key, balance binary_float default '0', user_id number (10));

alter table bank_user add constraint user_to_account_foreign_key foreign key (account_id) references bank_account (account_id) on delete cascade;

Insert into bank_account values (0, 20.0, null);

commit;