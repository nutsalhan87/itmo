import numpy as np
import matplotlib.pyplot as plt
import math

# Лагранж
def Ln(xy: np.ndarray, x: float) -> float:
    return sum([xy[1][i] * np.prod([(x - xy[0][j]) / (xy[0][i] - xy[0][j]) if i != j else 1 for j in range(0, xy.shape[1])]) for i in range(0, xy.shape[1])])

# Ньютон неравноотстоящий
def Nn(xy: np.ndarray, x: float) -> float:
    def f(xy: np.ndarray) -> float:
        if xy.shape == (2,):
            return xy[1]
        elif xy.shape == (2, 1):
            return xy[1][0]
        else:
            return (f(xy[:, 1:]) - f(xy[:, 0:-1])) / (xy[0][-1] - xy[0][0])
    return f(xy[:, 0]) + sum([f(xy[:, 0:k+1]) * np.prod([x - xy[0][j] for j in range(0, k)]) for k in range(1, xy.shape[1])])

# Таблица конечных разностей
def dy(xy: np.ndarray) -> np.ndarray:
    y = np.zeros((xy.shape[1], xy.shape[1]))
    y[:, 0] = xy[1]
    for i in range(1, xy.shape[1]):
        for j in range(0, xy.shape[1] - i):
            y[j][i] = y[j + 1][i - 1] - y[j][i - 1]
    return y

# Ньютон равноотстоящий
def Nnh(xy: np.ndarray, x: float) -> float:
    y = dy(xy)
    start = 0
    while start + 2 != xy.shape[1] and x > xy[0][start + 1]:
        start += 1
    is_left = (xy[0][start] + xy[0][start + 1]) / 2 >= x
    h = xy[0][1] - xy[0][0]
    t = np.zeros(xy.shape[1])
    t[0] = 1
    t[1] = (x - xy[0][start]) / h if is_left else (x - xy[0][-1]) / h
    for i in range(2, xy.shape[1]):
        t[i] = t[i - 1] * (t[1] - i + 1) / i if is_left else t[i - 1] * (t[1] + i - 1) / i
    return sum([y[start][k] * t[k] for k in range(0, xy.shape[1] - start)]) if is_left else sum([y[-k - 1][k] * t[k] for k in range(0, xy.shape[1])])

# Гаусс
def Pn(xy: np.ndarray, x: float) -> float:
    y = dy(xy)
    a = int(xy.shape[1] / 2)
    is_left = xy[0][a] > x
    h = xy[0][1] - xy[0][0]
    t = []
    t_coef = (x - xy[0][a]) / h
    t.append(1)
    t.append(t_coef)
    for i in range(2, xy.shape[1]):
        if is_left:
            t.append(t[-1] * (t_coef + i - 1) / ((i - 1) * 2))
            t.append(t[-1] * (t_coef - i + 1) / ((i - 1) * 2 + 1))
        else:
            t.append(t[-1] * (t_coef - i + 1) / ((i - 1) * 2))
            t.append(t[-1] * (t_coef + i - 1) / ((i - 1) * 2 + 1))
    return xy[1][a] + sum([t[k] * y[a - math.floor(k / 2)][k] for k in range(1, xy.shape[1])])