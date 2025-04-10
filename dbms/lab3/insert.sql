INSERT INTO precinct VALUES (58);
INSERT INTO precinct VALUES (59);

INSERT INTO district VALUES (77, 'Moscow');
INSERT INTO district VALUES (78, 'Saint-Petersburg');

INSERT INTO precinct_district VALUES (77, 58);
INSERT INTO precinct_district VALUES (78, 59);

INSERT INTO crime_type VALUES (5, 'Dismemberment');

INSERT INTO crime VALUES (10, 77, 4);
INSERT INTO crime VALUES (11, 78, 5);

INSERT INTO note VALUES (4, 6, 10, 'Ok');
INSERT INTO note VALUES (5, 6, 11, 'Not Ok');