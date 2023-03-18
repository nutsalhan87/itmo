"use strict"

let y = "";
let r;

async function initDocument() {
    document.getElementById("coords").onmousemove = ev => changePointLocationOnSVG(ev);
    document.getElementById("coords").onmouseup = mouseEvent => changeInputs(mouseEvent);
    document.getElementById("Y").onchange = () => validateTextForm();
    document.getElementById("submit-button").onclick = () => validateAndSubmit();
}

// Рисует точки по координатам сетки
async function changePointLocationOnCoordinates() {
    const x = getX();
    if (r === undefined || x == null || y === undefined) {
        return;
    }
    const xSVG = 126 * x / r + 188;
    const ySVG = 126 * y / r - 188;
    await changePointLocationOnSVG(xSVG, ySVG);
}

// Рисует точки по координатам на svg изображении
async function changePointLocationOnSVG(ev) {
    const userPoint = document.getElementById("user-point");
    userPoint.setAttribute("cx", ev.offsetX);
    userPoint.setAttribute("cy", ev.offsetY);

    if (r !== undefined) {
        const realPoint = document.getElementById("real-point");
        const realX = Math.round((ev.offsetX - 188) * r / 126) * 126 / r + 188;
        const realY = ev.offsetY;
        realPoint.setAttribute("cx", realX);
        realPoint.setAttribute("cy", realY);
    }
}

async function changeInputs(ev) {
    if (!isButtonFormValid()) {
        return;
    }
    const newX = String(Math.round((ev.offsetX - 188) * r / 126));
    const newY = ((188 - ev.offsetY) * r / 126).toFixed(4);
    let radios = document.getElementById("X").elements;
    for (let radio of radios) {
        radio.checked = radio.getAttribute("value") === newX;
    }
    document.getElementById("Y").value = newY;
    console.log("x: " + newX + " y: " + newY);
}

async function validateAndSubmit() {
    const isRadio = isRadioFormValid();
    const isButton = isButtonFormValid();
    const isText = isTextFormValid();

    if (!isRadio || !isButton || !isText) {
        console.log("Some inputs aren't valid");
        return;
    }

    document.getElementById("coord-form").requestSubmit();
}

function isRadioFormValid() {
    const isIt = getX() != null;
    if (!isIt) {
        highlightById("X");
    }
    return isIt;
}

function isTextFormValid() {
    const isIt = document.getElementById("Y").value !== "";
    if (!isIt) {
        highlightById("Y");
    }
    return isIt;
}

function isButtonFormValid() {
    const isIt = r !== undefined;
    if (!isIt) {
        highlightById("R");
    }
    return isIt;
}

function validateTextForm() {
    let currentY = Number(document.getElementById("Y").value);
    if (Number.isNaN(currentY) || currentY <= -5 || currentY >= 5) {
        document.getElementById("Y").value = y;
    } else {
        y = currentY;
    }
}

function setR(newR) {
    document.getElementById("hidden-R").setAttribute("value", newR);
    r = newR;
    changePointLocationOnCoordinates().then();
}

function getX() {
    let x = null;
    const xRadios = document.getElementsByName("X");
    for (const value of xRadios) {
        if (value.checked) {
            x = value.value;
        }
    }

    return x;
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