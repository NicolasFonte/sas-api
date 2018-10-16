-- initial users as example
insert into user values(1, 'vitor');
insert into user values(2, 'menders');
insert into user values(3, 'nicolas');

insert into favorite values(1, FALSE , 335983, 'test1', 10.0, '2018-10-03', 'Venom', 7.6, 1057);
insert into favorite values(2, FALSE, 335984, 'test2', 10.0, '2018-10-04', 'Venom', 8.6, 1058);
insert into favorite values(3, FALSE, 335985, 'test3', 10.0, '2018-10-05', 'Venom', 9.6, 1059);

insert into user_favorite values(1, 1);

insert into user_favorite values(2, 1);
insert into user_favorite values(2, 2);

insert into user_favorite values(3, 1);
insert into user_favorite values(3, 2);
insert into user_favorite values(3, 3);
