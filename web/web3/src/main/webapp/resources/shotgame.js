"use strict"

function initDocument() {
    document.getElementById("coords").onmousemove = ev => changePointCoords(ev).then();
    document.getElementById("coords").onmouseup = ev => sendCoordinates(ev).then();
    document.body.onkeydown = ev => processInput(ev);
    document.getElementById("coord-form:X").addEventListener("change", () => redrawCoordinates().then());
    document.getElementById("coord-form:Y").addEventListener("change", () => redrawCoordinates().then());
    document.getElementById("coord-form:R_input").addEventListener("change", () => redrawCoordinates().then());
    redrawCoordinates().then();
    drawPoints().then();
    console.log("Init script completed!");
}

async function drawPoints() {
    const rows = document.getElementById("result-table").rows;
    for (let i = 1; i < rows.length; ++i) {
        const x = Number(rows[i].cells[2].innerText);
        const y = Number(rows[i].cells[3].innerText);
        const r = Number(rows[i].cells[4].innerText);
        const shotStatus = rows[i].cells[5].innerText;
        if (shotStatus === "ДА!!!") {
            drawPoint(true, x, y, r);
        } else if (shotStatus === "НЕТ(((") {
            drawPoint(false, x, y, r);
        }
    }
    console.log("All the points are drawn");
}

function drawLastPointInTable(data) {
    const rows = document.getElementById("result-table").rows;
    const shotStatus = rows[rows.length - 1].cells[5].innerText === "ДА!!!";
    const x = Number(rows[rows.length - 1].cells[2].innerText);
    const y = Number(rows[rows.length - 1].cells[3].innerText);
    const r = Number(rows[rows.length - 1].cells[4].innerText);
    drawPoint(shotStatus, x, y, r);
    console.log("Last point is drawn");
}

function drawPoint(shotStatus, x, y, r) {
    let point = shotStatus ? getGreenPoint() : getRedPoint();
    const xSVG = x * 200 / r + 300;
    const ySVG = 300 - y * 200 / r;
    point.setAttribute("cx", xSVG);
    point.setAttribute("cy", ySVG);
    const coords = document.getElementById("coords");
    coords.insertBefore(point, coords.lastElementChild);
}

function getRedPoint() {
    const redCircle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    redCircle.classList.add("red-point");
    redCircle.setAttribute("r", "5");
    return redCircle;
}

function getGreenPoint() {
    const greenCircle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
    greenCircle.classList.add("green-point");
    greenCircle.setAttribute("r", "5");
    return greenCircle;
}

async function sendCoordinates(ev) {
    const fitOffsetX = ev.offsetX * 4 / 3;
    const fitOffsetY = ev.offsetY * 4 / 3;
    const r = getR();
    const x = Math.round((fitOffsetX - 300) * 2 * r / 200) / 2;
    const y = ((300 - fitOffsetY) * r / 200).toFixed(1);
    setX(x);
    setY(y);
    document.getElementById("coord-form:submit-button").click();
}

async function processInput(ev) {
    if (ev.code === "ArrowLeft" || ev.code === "ArrowRight") {
        let x = getX();
        if (isNaN(Number(x))) {
            x = 0;
        }
        if (ev.code === "ArrowLeft") {
            setX(Number(x) - 0.5);
        } else {
            setX(Number(x) + 0.5);
        }
    } else if (ev.code === "ArrowUp" || ev.code === "ArrowDown") {
        let y = getY();
        if (isNaN(Number(y))) {
            y = 0;
        }
        if (ev.code === "ArrowDown") {
            setY(Number(y) - 0.1);
        } else {
            setY(Number(y) + 0.1);
        }
    }
}

async function changePointCoords(ev) {
    const r = getR();
    const fitOffsetX = ev.offsetX * 4 / 3;
    const fitOffsetY = ev.offsetY * 4 / 3;
    const x = Math.round((fitOffsetX - 300) * 2 * r / 200) / 2;
    const y = ((300 - fitOffsetY) * r / 200).toFixed(1);
    redrawXCoordinate(x).then();
    redrawYCoordinate(y).then();
}

async function redrawCoordinates() {
    redrawXCoordinate(null).then();
    redrawYCoordinate(null).then();
}

async function redrawXCoordinate(x) {
    if (x == null) {
        x = getX();
    }
    const xSVG = x * 200 / getR() + 300;
    document.getElementById("real-point").setAttribute("cx", xSVG.toString());
}

async function redrawYCoordinate(y) {
    if (y == null) {
        y = getY();
    }
    const ySVG = 300 - y * 200 / getR();
    document.getElementById("real-point").setAttribute("cy", ySVG.toString());
}

function getX() {
    let radios = document.getElementsByName("coord-form:X");
    for (let i = 0; i < radios.length; ++i) {
        if (radios[i].checked === true) {
            return radios[i].value;
        }
    }
}

function getY() {
    return document.getElementById("coord-form:Y").value;
}

function getR() {
    return document.getElementById("coord-form:R_input").value;
}

function setX(newX) {
    if (newX < -2) {
        newX = -2;
    }
    if (newX > 2) {
        newX = 2;
    }
    let radios = document.getElementsByName("coord-form:X");
    for (let i = 0; i < radios.length; ++i) {
        if (Number(radios[i].value) === Math.round(newX * 2) / 2) {
            radios[i].checked = true;
            radios[i].dispatchEvent(new Event("click"));
            document.getElementById("coord-form:X").dispatchEvent(new Event("change"));
        } else {
            radios[i].checked = false;
        }
    }
}

function setY(newY) {
    if (newY < -3) {
        newY = -3;
    }
    if (newY > 3) {
        newY = 3;
    }
    const fieldY = document.getElementById("coord-form:Y");
    fieldY.value = Number(newY).toFixed(1);
    fieldY.dispatchEvent(new Event("change"));
}

function setR(newR) {
    if (newR < 0.1) {
        newR = 0.1;
    }
    if (newR > 3) {
        newR = 3;
    }
    const fieldR = document.getElementById("coord-form:R_input");
    fieldR.value = newR;
    fieldR.dispatchEvent(new Event("change"));
}

function highlightById(id) {
    const keyframes = [
        {transform: 'scale(1)'},
        {transform: 'scale(1.2)'},
        {transform: 'scale(0.8)'},
        {transform: 'scale(1)'}
    ];

    const options = {
        duration: 300,
        iteration: 1
    }

    const element = document.getElementById(id);
    element.animate(keyframes, options);
}