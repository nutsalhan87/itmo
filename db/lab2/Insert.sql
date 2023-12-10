insert into human values (DEFAULT, 'John', 80);
insert into human values (DEFAULT, 'Kolya', 70);
insert into human values (DEFAULT, 'Misha', 1000);
insert into human values (DEFAULT, 'Lesha', 20);
insert into human values (DEFAULT, 'Dima', 50);
insert into crew values (DEFAULT, 100);
insert into crew values (DEFAULT, 50);
insert into crew_composition values (1, 1, 'Captain');
insert into crew_composition values (1, 2, DEFAULT);
insert into crew_composition values (1, 3, 'Kok');
insert into crew_composition values (2, 4, 'Captain');
insert into crew_composition values (2, 5, 'Clown');
-- test: insert into crew_composition values (1, 3);
-- test: insert into crew_composition values (3, 3);

insert into spaceship values (DEFAULT, 'Nautilus', 3, 55, 75, 150);
insert into spaceship values (DEFAULT, 'Galka', 2, 35, 40, 75);
insert into crew_of_spaceship values (1, 1);
insert into crew_of_spaceship values (2, 2);
-- test: insert into crew_of_spaceship values (1, 2);
-- test: insert into crew_of_spaceship values (3, 3);

insert into planet values ('David Bowie', 20);
insert into planet values ('Aurora', 50);
insert into planet values ('Nirn', 30);
insert into planet_distance values ('David Bowie', 'Aurora', 40);
insert into planet_distance values ('David Bowie', 'Nirn', 50);
insert into planet_distance values ('Aurora', 'Nirn', 70);
insert into planet_distance values ('Aurora', 'David Bowie', 80);
insert into planet_distance values ('Nirn', 'David Bowie', 120);
insert into planet_distance values ('Nirn', 'Aurora', 140);
-- test: insert into planet_distance values ('Nirn', 'Aurora', 170);
-- test: insert into planet_distance values ('Earth', 'Aurora', 30000);

insert into spaceship_on_planet values (1, 'David Bowie');
insert into spaceship_on_planet values (2, 'Aurora');
-- test: insert into spaceship_on_planet values (2, 'Nirn');