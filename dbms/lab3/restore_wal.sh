export PGPORT=9029
export PGHOST='127.0.0.1'
export PGDATA=$HOME/u08/dnk13
export PGPASSWORD
PGPASSWORD=$(head -n 1 "$HOME"/lab2/pwfile)
wal_dir=$HOME/u02/dnk13
indexts_dir=$HOME/u03/dnk13

re='^[0-9]+$'
if ! [[ $1 =~ $re ]] ; then
   echo "error: Pid must be a number" >&2; exit 1
fi
cd "$HOME"

PGHOST="" pg_basebackup -D "$HOME"/basebackup -T "$indexts_dir"="$HOME"/basebackup_indexts

psql -d brownbunny -f lab3/insert2.sql
psql -d brownbunny -c 'select pg_switch_wal();'

rec_time=$(psql -d brownbunny -c 'select now();' | head -n 3 | tail -n 1 | cut -c2-)
psql -d brownbunny -f lab3/delete.sql

kill "$1"
sleep 1
cp -r "$wal_dir" ./actual_wal_copy
rm -rf "$PGDATA" "$indexts_dir" "$wal_dir"
mv basebackup "$PGDATA"
mv basebackup_indexts "$indexts_dir"

rm -rf "$PGDATA"/pg_wal
mkdir "$wal_dir"
ln -s "$wal_dir" "$PGDATA"/pg_wal

ts_dir_internal=$(ls "$PGDATA"/pg_tblspc) # здесь должно быть одно значение
rm -rf "$PGDATA"/pg_tblspc/"$ts_dir_internal"
ln -s "$indexts_dir" "$PGDATA"/pg_tblspc/"$ts_dir_internal"

cp -r actual_wal_copy/* "$wal_dir"/
touch "$PGDATA"/recovery.signal
cd lab2
postgres -c recovery_target_time="'""$rec_time""'" -c config_file="$PWD"/postgresql.conf -c hba_file="$PWD"/pg_hba.conf &