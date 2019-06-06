create database stock_trading_system;
use stock_trading_system;
create user stockadmin2 identified by '123456';
grant all on stock_trading_system.* to 'stockadmin2'@'%';

create table financeTable(
FinID bigint unique,
securityID varchar(20),
password varchar(20),
balance double unsigned,
interest double,
state bool,
primary key (finID)
);

create table financeLog (
actionID bigint,
financeID bigint,
actionTime DATETIME,
changeAmount double,
comment varchar(30),
primary key (actionID)
);

create table interestRate(
rate double
);