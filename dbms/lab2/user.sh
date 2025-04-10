#!/usr/bin/env bash

export PGDATA=$HOME/u08/dnk13
export PGINDEXTS=$HOME/u03/dnk13
export DBNAME=brownbunny
export PGHOST='127.0.0.1'
export PGPORT=9029
export PGPASSWORD
PGPASSWORD=$(head -n 1 pwfile)
mkdir -p "$PGINDEXTS"
createdb -T template0 $DBNAME
psql -c 'create tablespace indexts location '\'"$PGINDEXTS"\'';' -d postgres
psql -c 'create user milton password '\''hell'\'';' -d postgres
psql -c 'grant create on schema public to milton;' -d postgres
psql -c 'grant create on tablespace indexts TO milton;' -d postgres

PGPASSWORD=hell
psql -f ./scripts/create.sql -d $DBNAME -U milton
psql -f ./scripts/index.sql -d $DBNAME -U milton
psql -f ./scripts/insert.sql -d $DBNAME -U milton
psql -c 'alter index all in tablespace pg_default set tablespace indexts;' -d $DBNAME -U milton

PGPASSWORD=$(head -n 1 pwfile)
psql -c 'select ts.spcname as tablespace_name, cl.relname as name from pg_tablespace ts join pg_class cl on ts.oid = cl.reltablespace;' -d "$DBNA"