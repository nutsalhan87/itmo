import interpolate
import numpy as np
import matplotlib.pyplot as plt
import math

data_reader = open('data')
xy = np.array([list(map(float, data_reader.readline().split())), list(map(float, data_reader.readline().split()))])

print("Таблица конечных разностей:")
y = interpolate.dy(xy)
for yi in y:
    y_s = ""
    for yj in yi:
        y_s = f"{y_s} {yj:.3f}"
    print(y_s)
print()

print("Введите корень")
x_i = float(input())
print(f"""Значение по Лагранжу: {interpolate.Ln(xy, x_i):.3f}
Значение по Ньютону: {interpolate.Nnh(xy, x_i):.3f}
Разница между ними: {math.fabs(interpolate.Ln(xy, x_i) - interpolate.Nnh(xy, x_i))}
""")

x = np.linspace(xy[0][0] - 1, xy[0][-1] + 1)
y_L = [interpolate.Ln(xy, x_i) for x_i in x]
y_N = [interpolate.Nnh(xy, x_i) for x_i in x]
plt.plot(x, y_L, 'r')
plt.plot(x, y_N, 'b')
plt.scatter(xy[0], xy[1])
plt.show()