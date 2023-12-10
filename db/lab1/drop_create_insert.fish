for f in Drop Create Insert;
    psql -U postgres -f "$f.sql";
end