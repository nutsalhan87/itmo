export PGDATA=$HOME/u08/dnk13
PGENCODING=WIN1251
PGLOCALE=ru_RU.CP1251
PGWALDIR=$HOME/u02/dnk13
initdb -E $PGENCODING --locale=$PGLOCALE -X "$PGWALDIR" --pwfile=pwfile

export PGPORT=9029
postgres -c config_file="$PWD"/postgresql.conf -c hba_file="$PWD"/pg_hba.conf &
echo postgres server pid: $!