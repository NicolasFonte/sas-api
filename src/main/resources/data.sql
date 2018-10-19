-- initial users as example
insert into user values(1, 'vitor');
insert into user values(2, 'mendes');
insert into user values(3, 'nicolas');

insert into favorite values(1, FALSE, 'most favorite', 15.0, '2018-10-03', 'title1',  335983, 14.5, 100);
insert into favorite values(2, FALSE, 'second favorite', 12.0, '2018-10-04', 'title2',  335985, 18.5, 150);
insert into favorite values(3, FALSE, 'third favorite', 20.0, '2018-10-05', 'title3',  335986, 20.5, 200);

insert into user_favorite values(1, 1);

insert into user_favorite values(2, 1);
insert into user_favorite values(2, 2);

insert into user_favorite values(3, 1);
insert into user_favorite values(3, 2);
insert into user_favorite values(3, 3);
