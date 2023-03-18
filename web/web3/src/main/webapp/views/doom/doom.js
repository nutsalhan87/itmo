var commonArgs = ["-iwad", "doom1.wad", "-window", "-nogui", "-nomusic", "-config", "default.cfg", "-servername", "doomflare"];

var Module = {
    onRuntimeInitialized: () => {
        callMain(commonArgs);
    },
    noInitialRun: true,
    preRun: () => {
        Module.FS.createPreloadedFile("", "doom1.wad", "doom1.wad", true, true);
        Module.FS.createPreloadedFile("", "default.cfg", "default.cfg", true, true);
    },
    printErr: function (text) {
        if (arguments.length > 1) text = Array.prototype.slice.call(arguments).join(" ");
        console.error(text);
    },
    canvas: (function () {
        var canvas = document.getElementById("canvas");
        canvas.addEventListener(
            "webglcontextlost",
            function (e) {
                alert("WebGL context lost. You will need to reload the page.");
                e.preventDefault();
            },
            false
        );
        return canvas;
    })(),
    print: function (text) {
        console.log(text);
    },
    setStatus: function (text) {
        console.log(text);
    },
    totalDependencies: 0,
    monitorRunDependencies: function (left) {
        this.totalDependencies = Math.max(this.totalDependencies, left);
        Module.setStatus(left ? "Preparing... (" + (this.totalDependencies - left) + "/" + this.totalDependencies + ")" : "All downloads complete.");
    },
};

window.onerror = function (event) {
    Module.setStatus("Exception thrown, see JavaScript console");
    Module.setStatus = function (text) {
        if (text) Module.printErr("[post-exception status] " + text);
    };
};