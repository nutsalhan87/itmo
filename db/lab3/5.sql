SELECT MIN(EXTRACT(years FROM justify_interval(LOCALTIMESTAMP - Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ))) FROM Н_УЧЕНИКИ 
    JOIN Н_ЛЮДИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД 
    WHERE Н_УЧЕНИКИ.ГРУППА = '1100';

SELECT ГРУППА as Группа, ROUND(AVG(EXTRACT(years FROM justify_interval(LOCALTIMESTAMP - Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ)))) AS "Средний возраст"
    FROM Н_УЧЕНИКИ
    JOIN Н_ЛЮДИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД
    GROUP BY ГРУППА 
    HAVING ROUND(AVG(EXTRACT(years FROM justify_interval(LOCALTIMESTAMP - Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ)))) = (
        SELECT MIN(EXTRACT(years FROM justify_interval(LOCALTIMESTAMP - Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ))) FROM Н_УЧЕНИКИ 
            JOIN Н_ЛЮДИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД 
            WHERE Н_УЧЕНИКИ.ГРУППА = '1100'
    );