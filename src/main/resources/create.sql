create table if not exists users(regno varchar unique, name text);

create table if not exists balance(regno varchar, currency text, amount Number);


insert into users select   '1','user testowy' where not exists (select null from users where regno = '1');
insert into balance select '1','PLN',30 where not exists (select null from balance where regno = '1' and currency = 'PLN');
insert into balance select '1','USD',100 where not exists (select null from balance where regno = '1' and currency = 'USD');