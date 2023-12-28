INSERT INTO precinct VALUES (41);
INSERT INTO precinct VALUES (57);

--                        (id,       name,             birthDate,           race)
INSERT INTO person VALUES (DEFAULT, 'Harrier du Bois', make_date(7, 1, 1), 'KOJKO');             -- 1
INSERT INTO person VALUES (DEFAULT, 'Dick Mullen', make_date(5, 4, 11), 'ORANJE');               -- 2
INSERT INTO person VALUES (DEFAULT, 'Raphaël Ambrosius Costeau', make_date(7, 1, 1), 'KOJKO');   -- 3
INSERT INTO person VALUES (DEFAULT, 'Tequila Sunset', make_date(7, 1, 1), 'KOJKO');              -- 4
INSERT INTO person VALUES (DEFAULT, 'Harry', make_date(7, 1, 1), 'KOJKO');                       -- 5
INSERT INTO person VALUES (DEFAULT, 'Kim Kitsuragi', make_date(8, 3, 7), 'SEOLITE');             -- 6
INSERT INTO person VALUES (DEFAULT, 'Ptolemaios Pryce', make_date(-7, 1, 1), 'REVACHOL');        -- 7
INSERT INTO person VALUES (DEFAULT, 'Robert Kurvitz', make_date(-10, 3, 2), NULL);                -- 8
INSERT INTO person VALUES (DEFAULT, 'Titus Hardie', make_date(12, 4, 4), 'ZSIEMSK');             -- 9
INSERT INTO person VALUES (DEFAULT, 'Jean-Heron Vicquemare', make_date(16, 9, 24), 'OZONNE');    -- 10
INSERT INTO person VALUES (DEFAULT, 'Klaasje', make_date(20, 6, 11), 'ORANJE');                  -- 11
INSERT INTO person VALUES (DEFAULT, 'Judit Minot', make_date(23, 2, 27), 'ORANJE');              -- 12
INSERT INTO person VALUES (DEFAULT, 'Lena', make_date(-5, 2, 13), 'GRAAD');                      -- 13
INSERT INTO person VALUES (DEFAULT, 'Rene Arnoux', make_date(-27, 8, 7), 'SEMENINE');            -- 14
INSERT INTO person VALUES (DEFAULT, 'Joyce Messier', make_date(3, 6, 11), 'REVACHOL');           -- 15
INSERT INTO person VALUES (DEFAULT, 'Jean-Luc Measurehead', make_date(19, 2, 16), 'SEMENINE');   -- 16
INSERT INTO person VALUES (DEFAULT, 'Billie Mejean', make_date(13, 3, 11), 'REVACHOL');          -- 17

--                           (id,       rank,    series,          seriesDateOfIssue,    person, precinct)
INSERT INTO policeman VALUES (DEFAULT, 'MAJOR', 'REV07-56-03-JAM41', make_date(45, 1, 1), 7, 41);    -- 1 Pryce
INSERT INTO policeman VALUES (DEFAULT, 'MINOR', 'REV12-65-05-JAM41', make_date(50, 11, 7), 1, 41);   -- 2 Harrier
INSERT INTO policeman VALUES (DEFAULT, 'MINOR', 'REV13-15-04-JAM41', make_date(48, 5, 4), 10, 41);   -- 3 Vicquemare
INSERT INTO policeman VALUES (DEFAULT, 'MINOR', 'REV14-44-03-JAM41', make_date(49, 9, 7), 12, 41);   -- 4 Judit Minot
INSERT INTO policeman VALUES (DEFAULT, 'MAJOR', 'REV08-55-04-JAM57', make_date(46, 3, 1), 8, 57);    -- 5 Kurvitz
INSERT INTO policeman VALUES (DEFAULT, 'MINOR', 'REV09-53-11-JAM57', make_date(49, 5, 17), 6, 57);   -- 6 Kim Kitsuragi
INSERT INTO policeman VALUES (DEFAULT, 'MINOR', 'REV12-65-05-JAM57', make_date(50, 11, 7), 5, 57);   -- 7 Harry

--  (policeman, hashed)
INSERT INTO auth
VALUES
    (1, crypt('1234', gen_salt('bf'))),
    (2, crypt('2345', gen_salt('bf'))),
    (3, crypt('3456', gen_salt('bf'))),
    (4, crypt('4567', gen_salt('bf'))),
    (5, crypt('5678', gen_salt('bf'))),
    (6, crypt('6789', gen_salt('bf'))),
    (7, crypt('7890', gen_salt('bf')));

INSERT INTO district VALUES (DEFAULT, 'Martinaise');                -- 1
INSERT INTO district VALUES (DEFAULT, 'The Pox');                   -- 2
INSERT INTO district VALUES (DEFAULT, 'Villalobos');                -- 3
INSERT INTO district VALUES (DEFAULT, 'Central Jamrock');           -- 4
INSERT INTO district VALUES (DEFAULT, 'Grand Couron');              -- 5
INSERT INTO district VALUES (DEFAULT, 'Old South');                 -- 6
INSERT INTO district VALUES (DEFAULT, 'The Valley of the Dogs');    -- 7
INSERT INTO district VALUES (DEFAULT, 'Eminent Domain');            -- 8
INSERT INTO district VALUES (DEFAULT, 'Burnt Out Quarter');         -- 9
INSERT INTO district VALUES (DEFAULT, 'Coal City');                 -- 10

--                            (district, precinct)
INSERT INTO precinct_district VALUES (1, 41);
INSERT INTO precinct_district VALUES (2, 41);
INSERT INTO precinct_district VALUES (3, 41);
INSERT INTO precinct_district VALUES (1, 57);
INSERT INTO precinct_district VALUES (4, 57);

INSERT INTO crime_type VALUES (DEFAULT, 'Murdery');      -- 1
INSERT INTO crime_type VALUES (DEFAULT, 'Robbery');      -- 2
INSERT INTO crime_type VALUES (DEFAULT, 'Kidnapping');   -- 3
INSERT INTO crime_type VALUES (DEFAULT, 'Theft');        -- 4

--                     (id, district, type)
INSERT INTO crime VALUES (DEFAULT, 1, 1);   -- 1
INSERT INTO crime VALUES (DEFAULT, 1, 2);   -- 2
INSERT INTO crime VALUES (DEFAULT, 1, 3);   -- 3
INSERT INTO crime VALUES (DEFAULT, 1, 4);   -- 4
INSERT INTO crime VALUES (DEFAULT, 2, 1);   -- 5
INSERT INTO crime VALUES (DEFAULT, 2, 2);   -- 6
INSERT INTO crime VALUES (DEFAULT, 3, 3);   -- 7
INSERT INTO crime VALUES (DEFAULT, 4, 3);   -- 8
INSERT INTO crime VALUES (DEFAULT, 4, 4);   -- 9

--                         (crime, state, policeman_major)
INSERT INTO crime_case VALUES (1, 'ON_WORK', 1);
INSERT INTO crime_case VALUES (2, 'ON_WORK', 5);
INSERT INTO crime_case VALUES (5, 'FREEZE', 1);
INSERT INTO crime_case VALUES (8, 'ON_WORK', 5);

--                    (p_major, p_minor, crime, status)
INSERT INTO policeman_case VALUES (1, 2, 1, 'ASSIGNED');
INSERT INTO policeman_case VALUES (5, 6, 1, 'ASSIGNED');
INSERT INTO policeman_case VALUES (1, 2, 2, 'REMOVED');
INSERT INTO policeman_case VALUES (1, 3, 2, 'ASSIGNED');
INSERT INTO policeman_case VALUES (1, 4, 2, 'ASSIGNED');
INSERT INTO policeman_case VALUES (5, 7, 8, 'ASSIGNED');

--                   (id, policeman, crime, note)
INSERT INTO note VALUES (DEFAULT, 2, 1, 'There is hanged man on the tree');                     -- 1
INSERT INTO note VALUES (DEFAULT, 2, 1, 'I want to sing The Smallest Church in Saint-Saens');   -- 2
INSERT INTO note VALUES (DEFAULT, 6, 1, 'Harrier Du Bois has memory loss');                     -- 3

--                               (crime, person, policeman, relation, note)
INSERT INTO person_relevant_to_case VALUES (1, 3, 2, 'SUSPECT', 'He killed him');
INSERT INTO person_relevant_to_case VALUES (1, 4, 2, 'SUSPECT', 'Drunk driving');
INSERT INTO person_relevant_to_case VALUES (1, 9, 6, 'WITNESS', 'Told about some circumstances');