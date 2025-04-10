CREATE INDEX idx_precinct_district_fk_precinct ON precinct_district(fk_precinct);
CREATE INDEX idx_precinct_district_fk_district ON precinct_district(fk_district);
CREATE INDEX idx_crime_fk_district ON crime(fk_district);
CREATE INDEX idx_crime_fk_type ON crime(fk_type);
CREATE INDEX idx_note_fk_policeman ON note(fk_policeman);
CREATE INDEX idx_note_fk_crime ON note(fk_crime);