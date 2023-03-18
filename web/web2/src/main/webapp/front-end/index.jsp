<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WEB TWO</title>
    <link rel="stylesheet" href="front-end/main.css">
</head>

<body onload="initDocument()">
<table id="main-table">
    <tr>
        <td class="center-align" colspan="2">
            <h1>Нуцалханов Нуцалхан P32101. Вариант - 10005</h1>
        </td>
    </tr>
    <tr>
        <td rowspan="3" class="right-align">
            <svg id="coords" height="375" width="375">
                <rect class="shape" x="63" y="63" width="125" height="125"/>
                <polygon class="shape" points="313,188 188,188 188,250"/>
                <path class="shape" d="M188,188 L188,125 A63,63 1 0,1 250,188 z"/>
                <line x1="188" y1="5" x2="188" y2="370"></line>
                <line y1="188" x1="5" y2="188" x2="370"/>
                <polygon points="188,0 195,15  181,15"/>
                <polygon points="375,188 360,195  360,181"/>
                <line x1="184" x2="192" y1="63" y2="63"/>
                <line x1="184" x2="192" y1="125" y2="125"/>
                <line x1="184" x2="192" y1="250" y2="250"/>
                <line x1="184" x2="192" y1="313" y2="313"/>
                <line y1="184" y2="192" x1="63" x2="63"/>
                <line y1="184" y2="192" x1="125" x2="125"/>
                <line y1="184" y2="192" x1="250" x2="250"/>
                <line y1="184" y2="192" x1="313" x2="313"/>
                <text x="200" y="15">y</text>
                <text x="360" y="176">x</text>
                <text x="200" y="68">R</text>
                <text x="200" y="130">R/2</text>
                <text x="200" y="255">-R/2</text>
                <text x="200" y="318">-R</text>
                <text x="53" y="176">-R</text>
                <text x="110" y="176">-R/2</text>
                <text x="240" y="176">R/2</text>
                <text x="308" y="176">R</text>
                <circle id="user-point" cx="-10" cy="-10" r="5"/>
                <circle id="real-point" cx="-10" cy="-10" r="5"/>
            </svg>
        </td>
        <td class="tall-cell"></td>
    </tr>
    <tr>
        <td>
            <form action="" method="post" id="coord-form">
                <label for="X">Выберите X</label> <br>
                <fieldset id="X">
                    <input type="radio" name="X" value="-5"/><span>-5</span>
                    <input type="radio" name="X" value="-4"/><span>-4</span>
                    <input type="radio" name="X" value="-3"/><span>-3</span>
                    <input type="radio" name="X" value="-2"/><span>-2</span>
                    <input type="radio" name="X" value="-1"/><span>-1</span>
                    <input type="radio" name="X" value="0"/><span>0</span>
                    <input type="radio" name="X" value="1"/><span>1</span>
                    <input type="radio" name="X" value="2"/><span>2</span>
                    <input type="radio" name="X" value="3"/><span>3</span>
                </fieldset>
                <br>
                <label for="Y">Задайте Y</label> <br>
                <input type="text" id="Y" name="Y" placeholder="(-5; 5)" maxlength="7"/> <br> <br>
                <label for="R">Выберите R</label> <br>
                <fieldset id="R">
                    <input type="button" value="1" onclick="setR(1)">
                    <input type="button" value="1.5" onclick="setR(1.5)">
                    <input type="button" value="2" onclick="setR(2)">
                    <input type="button" value="2.5" onclick="setR(2.5)">
                    <input type="button" value="3" onclick="setR(3)">
                </fieldset>
                <br>
                <input type="hidden" name="R" id="hidden-R"/> <br>
                <button type="button" id="submit-button">Отправить</button>
            </form>
        </td>
    </tr>
    <tr>
        <td class="tall-cell"></td>
    </tr>
    <tr>
        <td colspan="2">
            <table id="result-table">
                <thead>
                <tr>
                    <th id="time-column">Время</th>
                    <th>Время обработки</th>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Попал?</th>
                </tr>
                </thead>
                <tbody id="result-table-body">
                <jsp:useBean id="shots" scope="application" class="org.nutsalhan87.web2.beans.ShotHistory"/>
                <c:forEach var="shot" items="${shots.history}">
                    <tr>
                        <td>${shot.date}</td>
                        <td>${shot.time} мкс</td>
                        <td>${shot.x}</td>
                        <td>${shot.y}</td>
                        <td>${shot.r}</td>
                        <td>
                            <c:choose>
                                <c:when test="${shot.result == true}">ДА!!!</c:when>
                                <c:when test="${shot.result == false}">НЕТ(((</c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>
</table>
<script src="front-end/script.js"></script>
</body>

</html>