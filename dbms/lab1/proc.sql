-- select
--     cons.conname as "Имя ограничения", 
--     cons.contype as "Тип", 
--     cl.relname as "Имя таблицы",
--     attr.attname as "Имя столбца",
--     pg_get_constraintdef(cons.oid) as "Текст ограничения"
--     from pg_catalog.pg_class cl
--         join pg_catalog.pg_attribute attr on cl.oid = attr.attrelid
--         join pg_catalog.pg_constraint cons on cl.oid = cons.conrelid
--     where
--         attr.attnotnull is true and
--         cons.contype = 'c' and
--         attr.attnum = ANY(cons.conkey);

CREATE PROCEDURE l1() AS $l1$
DECLARE
    res_row RECORD;
    cnt int;
BEGIN
    cnt := 0;
    RAISE NOTICE 'Номер | Имя ограничения | Тип | Имя таблицы | Имя столбца | Текст ограничения';
    RAISE NOTICE '-----------------------------------------------------------------------------';
    FOR res_row IN
        SELECT
            cons.conname AS cons_name, 
            cons.contype AS cons_type, 
            cl.relname AS table_name,
            attr.attname AS col_name,
            pg_get_constraintdef(cons.oid) AS cons_def
            FROM pg_catalog.pg_class cl
                JOIN pg_catalog.pg_attribute attr ON cl.oid = attr.attrelid
                JOIN pg_catalog.pg_constraint cons ON cl.oid = cons.conrelid
            WHERE
                attr.attnotnull IS TRUE AND
                cons.contype = 'c' AND
                attr.attnum = ANY(cons.conkey)
    LOOP
        cnt := cnt + 1;
        RAISE NOTICE '% % % % % %', cnt, res_row.cons_name, res_row.cons_type, res_row.table_name, res_row.col_name, res_row.cons_def;
    END LOOP;
END;
$l1$ LANGUAGE plpgsql;
