DELETE FROM note WHERE id = 5;
DELETE FROM crime WHERE id = 11;
DELETE FROM crime_type WHERE id = 5;
DELETE FROM precinct_district WHERE fk_district = 78 AND fk_precinct = 59;
DELETE FROM district WHERE id = 78;
DELETE FROM precinct WHERE number = 59;