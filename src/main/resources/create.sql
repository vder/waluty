create table users(regno text unique, name String);

create table balance(regno text, currency text, amount BigDecimal);


insert into users values ('1','user testowy');
insert into balance(1,'PLN',30);
insert into balance(1,'USD',100);