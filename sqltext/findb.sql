
use stock_trading_system;
create user 'stockadmin'@'%' identified by '123456';
grant all on stockdb.* to 'stockadmin'@'%';

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
primary key (actionID),
foreign key (financeID)
references financeTable(finID)
);

create table interestRate(
rate double
);