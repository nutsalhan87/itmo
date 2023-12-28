for f in drop create insert;
    psql -U postgres -f "$f.sql" 1>/dev/null;
end