export class Coordinates {
    private _x: number = 0;
    private _y: number = 0;
    private _r: number = 2;

    set x(x: number) {
        if (isNaN(x)) {
            x = 0;
        } else if (x < -5) {
            x = -5;
        } else if (x > 5) {
            x = 5;
        }
        this._x = x;
    }

    get x(): number {
        return this._x;
    }

    set y(y: number) {
        if (isNaN(y)) {
            y = 0;
        } else if (y < -5) {
            y = -5;
        } else if (y > 3) {
            y = 3;
        }
        this._y = y;
    }

    get y(): number {
        return this._y;
    }

    set r(r: number) {
        if (isNaN(r) || r < 0) {
            r = 0;
        } else if (r > 5) {
            r = 5;
        }
        this._r = r;
    }

    get r(): number {
        return this._r;
    }
}