SELECT Н_ЛЮДИ.ИД, Н_СЕССИЯ.ЧЛВК_ИД 
    FROM Н_ЛЮДИ 
    RIGHT JOIN Н_СЕССИЯ ON Н_ЛЮДИ.ИД = Н_СЕССИЯ.ЧЛВК_ИД 
    WHERE Н_ЛЮДИ.ФАМИЛИЯ = 'Иванов' 
        AND Н_СЕССИЯ.ЧЛВК_ИД > 100012;

CREATE INDEX ON Н_ЛЮДИ (ИД);
CREATE INDEX ON Н_ЛЮДИ USING HASH (ФАМИЛИЯ);
CREATE INDEX ON Н_СЕССИЯ (ЧЛВК_ИД);