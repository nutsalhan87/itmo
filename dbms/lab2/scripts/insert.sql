INSERT INTO precinct VALUES (41);
INSERT INTO precinct VALUES (57);

INSERT INTO district VALUES (1, 'Martinaise');                -- 1
INSERT INTO district VALUES (2, 'The Pox');                   -- 2
INSERT INTO district VALUES (3, 'Villalobos');                -- 3
INSERT INTO district VALUES (4, 'Central Jamrock');           -- 4
INSERT INTO district VALUES (5, 'Grand Couron');              -- 5
INSERT INTO district VALUES (6, 'Old South');                 -- 6
INSERT INTO district VALUES (7, 'The Valley of the Dogs');    -- 7
INSERT INTO district VALUES (8, 'Eminent Domain');            -- 8
INSERT INTO district VALUES (9, 'Burnt Out Quarter');         -- 9
INSERT INTO district VALUES (10, 'Coal City');                -- 10

--                            (district, precinct)
INSERT INTO precinct_district VALUES (1, 41);
INSERT INTO precinct_district VALUES (2, 41);
INSERT INTO precinct_district VALUES (3, 41);
INSERT INTO precinct_district VALUES (1, 57);
INSERT INTO precinct_district VALUES (4, 57);

INSERT INTO crime_type VALUES (1, 'Murdery');      -- 1
INSERT INTO crime_type VALUES (2, 'Robbery');      -- 2
INSERT INTO crime_type VALUES (3, 'Kidnapping');   -- 3
INSERT INTO crime_type VALUES (4, 'Theft');        -- 4

--                     (id, district, type)
INSERT INTO crime VALUES (1, 1, 1);   -- 1
INSERT INTO crime VALUES (2, 1, 2);   -- 2
INSERT INTO crime VALUES (3, 1, 3);   -- 3
INSERT INTO crime VALUES (4, 1, 4);   -- 4
INSERT INTO crime VALUES (5, 2, 1);   -- 5
INSERT INTO crime VALUES (6, 2, 2);   -- 6
INSERT INTO crime VALUES (7, 3, 3);   -- 7
INSERT INTO crime VALUES (8, 4, 3);   -- 8
INSERT INTO crime VALUES (9, 4, 4);   -- 9

--                   (id, policeman, crime, note)
INSERT INTO note VALUES (1, 2, 1, 'There is hanged man on the tree');                     -- 1
INSERT INTO note VALUES (2, 2, 1, 'I want to sing The Smallest Church in Saint-Saens');   -- 2
INSERT INTO note VALUES (3, 6, 1, 'Harrier Du Bois has memory loss');                     -- 3