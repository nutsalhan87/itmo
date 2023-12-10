import differential
import math
import numpy as np
import matplotlib.pyplot as plt
from typing import Callable

def f1(y0, x0) -> (Callable[[float, float], float], Callable[[float], float]):
    c = y0 * math.exp(math.cos(x0))
    f = lambda x, y: math.sin(x) * y
    F = lambda x: c * math.exp(-math.cos(x))
    return f, F

def f2(y0, x0) -> (Callable[[float, float], float], Callable[[float], float]):
    c = y0 * math.exp(x0) / math.pow(x0, x0)
    f = lambda x, y: y * math.log(x)
    F = lambda x: c * math.exp(-x) * math.pow(x, x)
    return f, F

def f3(y0, x0) -> (Callable[[float, float], float], Callable[[float], float]):
    c = (y0 - x0**2 - 2 * x0 - 2) * math.exp(-x0)
    f = lambda x, y: y - x**2
    F = lambda x: c * math.exp(x) + x**2 + 2 * x + 2
    return f, F

def f4(y0, x0):
    c = 1 / (y0 - 0.5) + x0
    f = lambda x, y: y**2 + x**2 - (y + x)
    F = lambda x: 1 / (c - x) + 0.5
    return f, F

def incorrect():
    print("Некорректный ввод")
    exit(0)

print("Введите y0, x0, xn, h через пробел")
y0, x0, xn, h = tuple(map(float, input().split()))
print("""Выберите функцию
1. y' = sin(x) * y
2. y' = y * ln(x)
3. y' = y - x^2""")

choice = input()
if choice == "1":
    f, F = f1(y0, x0)
elif choice == "2":
    f, F = f2(y0, x0)
elif choice == "3":
    f, F = f3(y0, x0)
else:
    # incorrect()
    f, F = f4(y0, x0)

x = np.linspace(x0, xn, int((xn - x0) / h + 1))
Y = np.vectorize(F)(x) / F(x0) * y0

fig, ax = plt.subplots(2, 3)

ax[0][0].plot(x, differential.Euler(y0, x, f), 'b')
ax[0][0].plot(x, Y, 'r')
ax[0][0].legend(['Приближенное решение', 'Точное решение'])
ax[0][0].title.set_text('Метод Эйлера')

ax[0][1].plot(x, differential.Euler2(y0, x, f), 'b')
ax[0][1].plot(x, Y, 'r')
ax[0][1].legend(['Приближенное решение', 'Точное решение'])
ax[0][1].title.set_text('Усовершенствованный метод Эйлера')

ax[0][2].plot(x, differential.RK(y0, x, f), 'b')
ax[0][2].plot(x, Y, 'r')
ax[0][2].legend(['Приближенное решение', 'Точное решение'])
ax[0][2].title.set_text('Метод Рунге-Кутта')

ax[1][0].plot(x, differential.Adams(y0, x, f), 'b')
ax[1][0].plot(x, Y, 'r')
ax[1][0].legend(['Приближенное решение', 'Точное решение'])
ax[1][0].title.set_text('Метод Адамса')

ax[1][1].plot(x, differential.AdamsCorrected(y0, x, f), 'b')
ax[1][1].plot(x, Y, 'r')
ax[1][1].legend(['Приближенное решение', 'Точное решение'])
ax[1][1].title.set_text('Метод Адамса с коррекцией')

ax[1][2].plot(x, differential.Miln(y0, x, f), 'b')
ax[1][2].plot(x, Y, 'r')
ax[1][2].legend(['Приближенное решение', 'Точное решение'])
ax[1][2].title.set_text('Метод Милна')

plt.show()