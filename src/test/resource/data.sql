--
-- create table IF NOT EXISTS clients
-- (
-- 	id bigint auto_increment not null
-- 		constraint clients_pkey
-- 			primary key,
-- 	name varchar(20) not null,
-- 	email varchar(30) not null,
-- 	password varchar(20) not null,
-- 	deposit double precision default 100,
-- 	surname varchar(20),
-- 	created bigint
-- );
-- create unique index clients_login_uindex
-- 	on clients (email);
	
	insert into clients(
	name, email, password, deposit, surname, created)
	VALUES ( 'Ololo','olololo@mail.ru', '123', 100, 'Ololo', 15720202021), ('Qaqaqa','qaqaqa@mail.ru', '123', 100, 'Qaqaqa', 15720202022);