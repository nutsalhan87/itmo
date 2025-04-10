export PGPORT=9029
export PGHOST='127.0.0.1'
export PGPASSWORD=pass

backup_dir=/var/db/postgres8/pgbackups/
newest_backup=$(find $backup_dir -type f -exec ls -t1 {} + | head -1)
find /var/db/postgres8/pgbackups/ -type f -exec ls -t1 {} + | head -1
gunzip -c "$newest_backup" | psql -d postgres