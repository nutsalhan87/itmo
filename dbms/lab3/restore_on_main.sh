export PGPORT=9029
export PGHOST='127.0.0.1'
export PGPASSWORD
PGPASSWORD=$(head -n 1 pwfile)

host=postgres8@pg132
newest_backup=$(ssh postgres8@pg132 'echo $(find /var/db/postgres8/pgbackups/ -type f -exec ls -t1 {} + | head -1)')
scp "$host:$newest_backup" ./backup.gz
gunzip -c backup.gz | psql -d postgres
rm backup.gz