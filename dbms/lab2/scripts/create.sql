CREATE TABLE precinct (
  number INTEGER PRIMARY KEY
);

CREATE TABLE district (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE precinct_district (
  fk_district INTEGER NOT NULL,
  fk_precinct INTEGER NOT NULL
);

CREATE TABLE crime_type (
  id INTEGER PRIMARY KEY,
  name TEXT
);

CREATE TABLE crime (
  id INTEGER PRIMARY KEY,
  fk_district INTEGER,
  fk_type INTEGER
);

CREATE TABLE note (
  id INTEGER PRIMARY KEY,
  fk_policeman INTEGER NOT NULL,
  fk_crime INTEGER NOT NULL,
  note TEXT NOT NULL
);
