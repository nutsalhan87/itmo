"use strict"

let y = "";
let r;

async function getTable() {
    const answer = await fetch("getTable.php", {
        method: "GET"
    });

    document.getElementById("result-table-body").innerHTML = await answer.text();
}

async function submit() {
    const isRadio = isRadioFormValid();
    const isButton = isButtonFormValid();
    const isText = isTextFormValid();

    if (!isRadio || !isButton || !isText) {
        console.log("Some inputs aren't valid");
        return;
    }

    const responseObject = {
        x: getX(),
        y: y,
        r: r
    };
    const answer = await fetch("checkHit.php", {
        method: "POST",
        body: JSON.stringify(responseObject)
    });
    
    document.getElementById("result-table-body").innerHTML += await answer.text();

    const point = document.getElementById("point");
    console.log(point);
    point.setAttribute("cx", `${188 + getX() / r * 2* 63}`);
    point.setAttribute("cy", `${188 - y / r  * 2 * 63}`);
}

function isRadioFormValid() {
    const isIt = getX() != null; 
    if (!isIt) {
        highlightById("X");
    }
    return isIt;
}

function isTextFormValid() {
    const isIt = document.getElementById("Y").value != "";
    if (!isIt) {
        highlightById("Y");
    }
    return isIt;
}

function isButtonFormValid() {
    const isIt = r != undefined;
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
    r = newR;
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