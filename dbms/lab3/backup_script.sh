# на основном узле
#   crontab -e
#   * * 1-31 * * bash /var/db/postgres8/lab3/backup_script.sh 
#
#   ssh-keygen -t rsa
#   cat .ssh/id_rsa.pub | ssh postgres8@pg132 'cat >> .ssh/authorized_keys'

export PGPORT=9029
export PGHOST='127.0.0.1'
export PGPASSWORD
PGPASSWORD=$(head -n 1 pwfile)

backup_filename="pgbackup_$(date +%d.%m.%Y-%H%M%S).gz"
host=postgres8@pg132
backup_dir=/var/db/postgres8/pgbackups/

pg_dumpall --clean --if-exists | gzip > "$backup_filename" # to restore: gunzip -c имя_файла.gz | psql -d postgres 
ssh $host "mkdir -p $backup_dir"
scp "$backup_filename" "$host:$backup_dir$backup_filename"
ssh $host "find $backup_dir -mtime +28 -delete"
rm "$backup_filename"