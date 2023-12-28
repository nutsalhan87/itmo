CREATE TYPE person_relation AS ENUM (
  'WITNESS',
  'SUSPECT'
);

CREATE TYPE case_state AS ENUM (
  'ON_WORK',
  'FREEZE',
  'CLOSE'
);

CREATE TYPE police_rank as ENUM (
  'MAJOR',
  'MINOR'
);

CREATE TYPE race as ENUM (
  'SAMARAN',
  'SEOLITE',
  'OCCIDENTAL',
  'KOJKO',
  'MODIAL',
  'PERIKARNASSIA',
  'MESSINA',
  'ORANJE',
  'MESQUE',
  'VESPER',
  'SUR-LA-CLEF',
  'UBI SENT',
  'GRAAD',
  'KONIGSTEIN',
  'ZSIEMSK',
  'YUGO-GRAAD',
  'IGAUNIJA',
  'ZEMLYA',
  'MIROVA',
  'ILMARRAN',
  'SEMENINE',
  'REVACHOL',
  'OZONNE',
  'VAASA',
  'SURU'
);

CREATE TYPE policeman_case_status as ENUM (
  'ASSIGNED',
  'REMOVED'
);

CREATE TABLE precinct (
  number INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY
);

CREATE TABLE person (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name TEXT,
  birthdate DATE,
  "race" race
);

-- полицейский должен ссылаться на человека, у которого есть имя
CREATE TABLE policeman (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  rank police_rank NOT NULL,
  series TEXT NOT NULL,
  date_of_issue DATE NOT NULL,
  fk_person INTEGER NOT NULL,
  fk_precinct INTEGER NOT NULL
);

CREATE FUNCTION check_has_name() RETURNS TRIGGER AS $check_has_name$
  DECLARE 
    name TEXT;
  BEGIN
    SELECT person.name INTO STRICT name FROM person WHERE person.id = NEW.fk_person;
    IF name IS NULL THEN
      RAISE EXCEPTION 'A policeman must be a person with name';
    END IF;
    RETURN NEW;
  END;
$check_has_name$ LANGUAGE plpgsql;

CREATE TABLE district (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE precinct_district (
  fk_district INTEGER NOT NULL,
  fk_precinct INTEGER NOT NULL
);

CREATE TABLE crime_type (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name TEXT
);

CREATE TABLE crime (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  fk_district INTEGER,
  fk_type INTEGER
);

-- проверка, что при изменении меняется не с closed
-- проверка, что создает дело major
-- может быть открыто, если преступление произошло в том же районе, к какому прикреплен участок, в котором работает полицейский
CREATE TABLE crime_case (
  fk_crime INTEGER PRIMARY KEY,
  state case_state NOT NULL,
  fk_policeman_major INTEGER NOT NULL
);

CREATE FUNCTION check_closed() RETURNS TRIGGER AS $check_closed$ 
  BEGIN
  IF OLD.state = 'CLOSE' THEN
    RAISE EXCEPTION 'The case can not be modified after closing';
  END IF;
  RETURN NEW;
  END;
$check_closed$ LANGUAGE plpgsql;
-- - update crime_case set state = 'close' where fk_crime = 1;
--    update crime_case set state = 'on_work' where fk_crime = 1;
--    update crime_case set fk_policeman_major = 1 where fk_crime = 1;

CREATE FUNCTION check_major() RETURNS TRIGGER AS $check_major$ 
  DECLARE
    rank police_rank;
  BEGIN
  SELECT policeman.rank INTO STRICT rank FROM policeman WHERE policeman.id = NEW.fk_policeman_major;
  IF rank <> 'MAJOR' THEN
    RAISE EXCEPTION 'Only major policeman can open a case';
  END IF;
  RETURN NEW;
  END;
$check_major$ LANGUAGE plpgsql;
-- - update crime_case set fk_policeman_major = 1 where fk_crime = 1;

CREATE FUNCTION check_district() RETURNS TRIGGER AS $check_district$ 
  DECLARE
    district_policeman INTEGER[];
    district_crime INTEGER;
  BEGIN
  district_policeman = ARRAY(
    SELECT precinct_district.fk_district FROM policeman JOIN precinct_district ON policeman.fk_precinct = precinct_district.fk_precinct 
      WHERE policeman.id = NEW.fk_policeman_major
    );
  SELECT crime.fk_district INTO STRICT district_crime FROM crime WHERE crime.id = NEW.fk_crime;
  IF NOT (district_crime = ANY(district_policeman)) THEN
    RAISE EXCEPTION 'Policeman can not create a case because crime happened in different district';
  END IF;
  RETURN NEW;
  END;
$check_district$ LANGUAGE plpgsql;
-- + update crime_case set fk_policeman_major = 4 where fk_crime = 1; пояснение: преступление с fk_crime 1 совершено в Мартинезе, к которому прикреплены оба участка
-- - update crime_case set fk_policeman_major = 4 where fk_crime = 2;

-- должно быть соотвествие званий
-- на crime должен быть открыт case
-- minor и major должны быть с одного участка
CREATE TABLE policeman_case (
  fk_policeman_major INTEGER,
  fk_policeman_minor INTEGER NOT NULL,
  fk_crime INTEGER NOT NULL,
  status policeman_case_status NOT NULL
);

CREATE FUNCTION check_ranks_and_precinct() RETURNS TRIGGER AS $check_ranks_and_precinct$
  DECLARE
    major_rank police_rank;
    minor_rank police_rank;
  BEGIN
    IF NEW.fk_policeman_major IS NOT NULL THEN
      SELECT policeman.rank INTO STRICT major_rank FROM policeman WHERE policeman.id = NEW.fk_policeman_major;
      IF major_rank = 'MINOR' THEN
        RAISE EXCEPTION 'Major policeman is not really one';
      END IF;
    END IF;
    SELECT policeman.rank INTO STRICT minor_rank FROM policeman WHERE policeman.id = NEW.fk_policeman_minor;
    IF minor_rank <> 'MINOR' THEN
      RAISE EXCEPTION 'Minor policeman is not really one';
    END IF;
    If (SELECT fk_precinct FROM policeman WHERE policeman.id = NEW.fk_policeman_major) 
      <>
      (SELECT fk_precinct FROM policeman WHERE policeman.id = NEW.fk_policeman_minor) THEN
      RAISE EXCEPTION 'Major and minor are from different precincts';
    END IF;
    RETURN NEW;
  END;
$check_ranks_and_precinct$ LANGUAGE plpgsql;
-- + update policeman_case set fk_policeman_major = NULL WHERE fk_policeman_minor = 1;
-- - update policeman_case set fk_policeman_major = 3 WHERE fk_policeman_minor = 1;
-- - update policeman_case set fk_policeman_minor = 4 WHERE fk_policeman_major = 2;
-- - update policeman_case set fk_policeman_minor = 3 WHERE fk_policeman_major = 2;

CREATE FUNCTION check_opened_case() RETURNS TRIGGER AS $check_opened_case$
  BEGIN
    IF NOT EXISTS (SELECT * FROM crime_case WHERE crime_case.fk_crime = NEW.fk_crime) THEN
      RAISE EXCEPTION 'There is no case opened on such crime';
    END IF;
    RETURN NEW;
  END;
$check_opened_case$ LANGUAGE plpgsql;
-- - update policeman_case set fk_crime = 4 WHERE fk_policeman_major = 2;

-- вносить заметки можно только тогда, когда дело в процессе
-- и когда полицейский назначен на него и не снят
-- на crime должен быть открыт case
CREATE TABLE note (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  fk_policeman INTEGER NOT NULL,
  fk_crime INTEGER NOT NULL,
  note TEXT NOT NULL
);

CREATE FUNCTION check_case_and_policeman() RETURNS TRIGGER AS $check_case_and_policeman$
  DECLARE 
    state case_state;
    policeman INTEGER;
    status policeman_case_status;
  BEGIN
    IF NEW.fk_policeman IS NOT NULL AND NOT EXISTS( -- проверка, что полицейский связан с делом
      SELECT * FROM policeman_case WHERE policeman_case.fk_policeman_minor = NEW.fk_policeman 
        AND policeman_case.fk_crime = NEW.fk_crime
    ) THEN
      RAISE EXCEPTION 'A policeman is not assigned to this case';
    END IF;
    SELECT policeman_case.status INTO STRICT status FROM policeman_case  -- проверка, что полицейского не отстранили от дела
      WHERE policeman_case.fk_policeman_minor = NEW.fk_policeman
        AND policeman_case.fk_crime = NEW.fk_crime;
    IF status = 'REMOVED' THEN
      RAISE EXCEPTION 'A policeman is removed from this case';
    END IF;
    SELECT crime_case.state INTO STRICT state FROM crime_case WHERE crime_case.fk_crime = NEW.fk_crime;
    IF state <> 'ON_WORK' THEN -- проверка, что дело в работе
      RAISE EXCEPTION 'Policeman can not make this action because case is %sd', state;
    END IF;

    RETURN NEW;
  END;
$check_case_and_policeman$ LANGUAGE plpgsql;
-- - update note set fk_policeman = 2 where fk_policeman = 1; A policeman is not assigned to this case
-- - insert into note VALUES (4, 3, 2, ''); A policeman is not assigned to this case
-- - update crime_case set state = 'close' where fk_crime = 1;
--    insert into note VALUES (4, 3, 1, ''); Policeman can not make this action because case is closesd

-- сделать триггер на запрет назначения полицейским самого себя в качестве лица, относящегося к делу
-- назначить кого-то к делу можно только если оно активно
-- на crime должен быть открыт case
CREATE TABLE person_relevant_to_case (
  fk_crime INTEGER,
  fk_person INTEGER,
  fk_policeman INTEGER NOT NULL,
  relation person_relation NOT NULL,
  note TEXT,
  PRIMARY KEY (fk_crime, fk_person)
);

CREATE FUNCTION check_self_assign() RETURNS TRIGGER AS $check_self_assign$
  DECLARE
    policeman_person INTEGER;
  BEGIN
    SELECT policeman.fk_person INTO policeman_person FROM policeman
      WHERE policeman.id = NEW.fk_policeman;
    IF policeman_person = NEW.fk_person THEN
      RAISE EXCEPTION 'A policeman cannot appoint himself as a person relevant to the case';
    END IF;
    RETURN NEW;
  END;
$check_self_assign$ LANGUAGE plpgsql;
-- - update person_relevant_to_case set fk_person = 6 where fk_policeman = 3;

CREATE TABLE auth (
  fk_policeman INTEGER UNIQUE NOT NULL,
  hashed TEXT NOT NULL
);

COMMENT ON COLUMN person_relevant_to_case.fk_policeman IS 'who assigned person';

COMMENT ON COLUMN policeman_case.fk_policeman_major IS 'who assigned minor';

COMMENT ON COLUMN policeman_case.fk_policeman_minor IS 'who assigned by major';

COMMENT ON COLUMN crime_case.fk_policeman_major IS 'who created case';

ALTER TABLE policeman ADD FOREIGN KEY (fk_person) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE policeman ADD FOREIGN KEY (fk_precinct) REFERENCES precinct (number) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE precinct_district ADD FOREIGN KEY (fk_district) REFERENCES district (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE precinct_district ADD FOREIGN KEY (fk_precinct) REFERENCES precinct (number) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE crime ADD FOREIGN KEY (fk_district) REFERENCES district (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE crime ADD FOREIGN KEY (fk_type) REFERENCES crime_type (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE crime_case ADD FOREIGN KEY (fk_crime) REFERENCES crime (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE crime_case ADD FOREIGN KEY (fk_policeman_major) REFERENCES policeman (id) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE person_relevant_to_case ADD FOREIGN KEY (fk_crime) REFERENCES crime (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE person_relevant_to_case ADD FOREIGN KEY (fk_person) REFERENCES person (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE person_relevant_to_case ADD FOREIGN KEY (fk_policeman) REFERENCES policeman (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE policeman_case ADD FOREIGN KEY (fk_policeman_major) REFERENCES policeman (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE policeman_case ADD FOREIGN KEY (fk_policeman_minor) REFERENCES policeman (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE policeman_case ADD FOREIGN KEY (fk_crime) REFERENCES crime (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE note ADD FOREIGN KEY (fk_policeman) REFERENCES policeman (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE note ADD FOREIGN KEY (fk_crime) REFERENCES crime (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE auth ADD FOREIGN KEY (fk_policeman) REFERENCES policeman (id) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TRIGGER check_has_name AFTER INSERT OR UPDATE on policeman
  FOR EACH ROW EXECUTE FUNCTION check_has_name();

CREATE TRIGGER check_closed AFTER UPDATE ON crime_case
  FOR EACH ROW EXECUTE FUNCTION check_closed();

CREATE TRIGGER check_major AFTER INSERT OR UPDATE ON crime_case
  FOR EACH ROW EXECUTE FUNCTION check_major();

CREATE TRIGGER check_district AFTER INSERT OR UPDATE ON crime_case
  FOR EACH ROW EXECUTE FUNCTION check_district();

CREATE TRIGGER check_ranks_and_precinct AFTER INSERT OR UPDATE on policeman_case
  FOR EACH ROW EXECUTE FUNCTION check_ranks_and_precinct();

CREATE TRIGGER check_opened_case AFTER INSERT OR UPDATE on policeman_case
  FOR EACH ROW EXECUTE FUNCTION check_opened_case();

CREATE TRIGGER check_case_and_policeman AFTER INSERT OR UPDATE on note
  FOR EACH ROW EXECUTE FUNCTION check_case_and_policeman();

CREATE TRIGGER check_opened_case AFTER INSERT OR UPDATE on note
  FOR EACH ROW EXECUTE FUNCTION check_opened_case();

CREATE TRIGGER check_self_assign AFTER INSERT OR UPDATE ON person_relevant_to_case
  FOR EACH ROW EXECUTE FUNCTION check_self_assign();

CREATE TRIGGER check_case_and_policeman AFTER INSERT OR UPDATE ON person_relevant_to_case
  FOR EACH ROW EXECUTE FUNCTION check_case_and_policeman();

CREATE TRIGGER check_opened_case AFTER INSERT OR UPDATE on person_relevant_to_case
  FOR EACH ROW EXECUTE FUNCTION check_opened_case();