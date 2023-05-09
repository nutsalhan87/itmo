import math
import approximation
import numpy as np
import matplotlib.pyplot as plt

file = open('input')
x = list(map(float, file.readline().split()))
y = list(map(float, file.readline().split()))
min_x = min(x)
max_x = max(x)
x_space = np.linspace(min_x, max_x, 100)
fig, ax = plt.subplots(2, 3)
for i in range(0, 2):
    for j in range(0, 3):
        ax[i][j].set_xlim([min_x, max_x])
        ax[i][j].scatter(x, y)
xy = np.vstack([x, y])
ls = approximation.LeastSquares(xy)
max_precision = []

ls.polynomial(1)
ax[0][0].set_title('Линейная аппроксимация')
ax[0][0].plot(x_space, np.vectorize(ls.f)(x_space))
print("""
Линейная аппроксимация:
Коэффициент вариации Пирсона: {:.3f}
Мера отклонения S: {:.3f}
Среднеквадратическое отклонение: {:.3f}
Коэффициент R^2: {:.3f}
Функция: {}
""".format(approximation.pearson_corr_coef(xy), ls.S(), ls.stddev(), ls.R2(), ls.f_str))
max_precision = [ls.stddev(), "линейная"]

ls.polynomial(2)
ax[0][1].set_title('Квадратичная аппроксимация')
ax[0][1].plot(x_space, np.vectorize(ls.f)(x_space))
print("""
Квадратичная аппроксимация:
Мера отклонения S: {:.3f}
Среднеквадратическое отклонение: {:.3f}
Коэффициент R^2: {:.3f}
Функция: {}
""".format(ls.S(), ls.stddev(), ls.R2(), ls.f_str))
if max_precision[0] > ls.stddev():
    max_precision = [ls.stddev(), "квадратичная"]

ls.polynomial(3)
ax[0][2].set_title('Кубическая аппроксимация')
ax[0][2].plot(x_space, np.vectorize(ls.f)(x_space))
print("""
Кубическая аппроксимация:
Мера отклонения S: {:.3f}
Среднеквадратическое отклонение: {:.3f}
Коэффициент R^2: {:.3f}
Функция: {}
""".format(ls.S(), ls.stddev(), ls.R2(), ls.f_str))
if max_precision[0] > ls.stddev():
    max_precision = [ls.stddev(), "кубическая"]

ax[1][0].set_title('Экспоненциальная аппроксимация')
if ls.exp():
    ax[1][0].plot(x_space, np.vectorize(ls.f)(x_space))
    print("""
Экспоненциальная аппроксимация:
Мера отклонения S: {:.3f}
Среднеквадратическое отклонение: {:.3f}
Коэффициент R^2: {:.3f}
Функция: {}
    """.format(ls.S(), ls.stddev(), ls.R2(), ls.f_str))
    if max_precision[0] > ls.stddev():
        max_precision = [ls.stddev(), "экспоненциальная"]
else:
    print("Экспоненциальная аппроксимация невозможна")

ax[1][1].set_title('Логарифмическая аппроксимация')
if ls.log():
    ax[1][1].plot(x_space, np.vectorize(ls.f)(x_space))
    print("""
Логарифмическая аппроксимация:
Мера отклонения S: {:.3f}
Среднеквадратическое отклонение: {:.3f}
Коэффициент R^2: {:.3f}
Функция: {}
    """.format(ls.S(), ls.stddev(), ls.R2(), ls.f_str))
    if max_precision[0] > ls.stddev():
        max_precision = [ls.stddev(), "логарифмическая"]
else:
    print("Логарифмическая аппроксимация невозможна")

ax[1][2].set_title('Степенная аппроксимация')
if ls.power():
    ax[1][2].plot(x_space, np.vectorize(ls.f)(x_space))
    print("""
Степенная аппроксимация:
Мера отклонения S: {:.3f}
Среднеквадратическое отклонение: {:.3f}
Коэффициент R^2: {:.3f}
Функция: {}
    """.format(ls.S(), ls.stddev(), ls.R2(), ls.f_str))
    if max_precision[0] > ls.stddev():
        max_precision = [ls.stddev(), "степенная"]
else:
    print("Степенная аппроксимация невозможна")

print("Лучшая аппроксимация - {}".format(max_precision[1]))
plt.show()