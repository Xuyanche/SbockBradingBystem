use stockdb;
insert into interestrate values (0.01);
insert into financetable values (1, "a", "123123", 50, 0, true);
insert into financetable values (2, "b", "111111", 100, 0, true),
(3, "c", "232323", 20, 0, false),
(4, "d", "111123", 70, 0, true);
insert into financelog values (1, 1, NOW(), -20, "draw from counter");