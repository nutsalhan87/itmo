import numpy as np
import math
from typing import Callable

# Эйлер
def Euler(y0: float, x: np.ndarray, f: Callable[[float, float], float]) -> list:
    y = [y0]
    for i in range(1, x.size):
        y.append(y[i - 1] + (x[i] - x[i - 1]) * f(x[i - 1], y[i - 1]))
    return y

# Эйлер усовершенствованный
def Euler2(y0: float, x: np.ndarray, f: Callable[[float, float], float]) -> list:
    y = [y0]
    for i in range(1, x.size):
        h = x[i] - x[i - 1]
        y.append(y[i - 1] + (h / 2) * (f(x[i - 1], y[i - 1]) + f(x[i], y[i - 1] + h * f(x[i - 1], y[i - 1]))))
    return y

# Рунге-Кутта
def RK(y0: float, x: np.ndarray, f: Callable[[float, float], float]) -> list:
    y = [y0]
    for i in range(1, x.size):
        h = x[i] - x[i - 1]
        k1 = h * f(x[i - 1], y[i - 1])
        k2 = h * f(x[i - 1] + h / 2, y[i - 1] + k1 / 2)
        k3 = h * f(x[i - 1] + h / 2, y[i - 1] + k2 / 2)
        k4 = h * f(x[i - 1] + h, y[i - 1] + k3)
        y.append(y[i - 1] + (k1 + 2 * (k2 + k3) + k4) / 6)
    return y

# Адамс
def Adams(y0: float, x: np.ndarray, f: Callable[[float, float], float]) -> list:
    y = RK(y0, x[0:4], f)
    for i in range(4, x.size):
        fi = f(x[i - 1], y[i - 1])
        fim1 = f(x[i - 2], y[i - 2])
        fim2 = f(x[i - 3], y[i - 3])
        fim3 = f(x[i - 4], y[i - 4])
        df = fi - fim1
        d2f = fi - 2 * fim1 + fim2
        d3f = fi + 3 * (-fim1 + fim2) - fim3
        h = x[i] - x[i - 1]
        y.append(y[i - 1] + h * fi + h**2 / 2 * df + 5 * h**3 / 12 * d2f + 3 * h**4 / 8 * d3f)
    return y

# Адамс с коррекцией
def AdamsCorrected(y0: float, x: np.ndarray, f: Callable[[float, float], float]) -> list:
    y = RK(y0, x[0:4], f)
    for i in range(4, x.size):
        fi = f(x[i - 1], y[i - 1])
        fim1 = f(x[i - 2], y[i - 2])
        fim2 = f(x[i - 3], y[i - 3])
        fim3 = f(x[i - 4], y[i - 4])
        h = x[i] - x[i - 1]
        y.append(y[i - 1] + h * (55 * fi - 59 * fim1 + 37 * fim2 - 9 * fim3) / 24)
    for i in range(4, x.size):
        fip1 = f(x[i], y[i])
        fi = f(x[i - 1], y[i - 1])
        fim1 = f(x[i - 2], y[i - 2])
        fim2 = f(x[i - 3], y[i - 3])
        y[i] = y[i - 1] + h * (9 * fip1 + 19 * fi - 5 * fim1 + fim2) / 24
    return y

# Милн
def Miln(y0: float, x: np.ndarray, f: Callable[[float, float], float]) -> list:
    e = 1e-5
    y = RK(y0, x[0:4], f)
    for i in range(4, x.size):
        h = x[i] - x[i - 1]
        y.append(y[i - 4] + 4 * h * (2 * f(x[i - 3], y[i - 3]) - f(x[i - 2], y[i - 1] + 2 * f(x[i - 1], y[i - 1]))) / 3)
        y_corrected = y[i - 2] + h * (f(x[i - 2], y[i - 2]) + 4 * f(x[i - 1], y[i - 1]) + f(x[i], y[i])) / 3
        while math.fabs(y[i] - y_corrected) >= e:
            y[i] = y_corrected
            y_corrected = y[i - 2] + h * (f(x[i - 2], y[i - 2]) + 4 * f(x[i - 1], y[i - 1]) + f(x[i], y[i])) / 3
        y[i] = y_corrected
    return y