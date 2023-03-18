const clock = document.getElementById("clock");

async function doClock() {
    clock.dispatchEvent(new Event("click"));
}