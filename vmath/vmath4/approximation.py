import math
import numpy as np
from typing import Callable

class LeastSquares:
    xy: np.ndarray
    n: int
    a: np.ndarray
    x_aprx: np.ndarray
    f: Callable[[float], float]
    f_str: str

    def __init__(self, xy: np.ndarray):
        self.xy = xy
        self.n = xy.shape[1]

    def polynomial(self, m: int):
        x_sums = [self.n] + [(self.xy[0, :]**i).sum() for i in range(1, 2 * m + 1)]
        xy_sums = np.array([(self.xy[0, :]**i * self.xy[1, :]).sum() for i in range(0, m + 1)])
        x_matrix = np.array([x_sums[0+i:m+1+i] for i in range(0, m+1)])
        self.a = np.flip(np.linalg.solve(x_matrix, xy_sums))
        self.f = lambda x: sum([x**(m - j) * self.a[j] for j in range(0, m + 1)])
        self.x_aprx = np.array([sum([self.xy[0, i]**(m - j) * self.a[j] for j in range(0, m + 1)]) for i in range(0, self.n)])
        self.f_str = "{:.3f}x{}".format(self.a[0], "" if m == 1 else "^{}".format(m))
        for i in range(1, m + 1):
            self.f_str += " {} {:.3f}x{}".format("+" if self.a[i] >= 0 else "-", math.fabs(self.a[i]), "" if m - i == 1 else "^{}".format(m - i)) if m - i != 0 \
                else " {} {:.3f}".format("+" if self.a[i] >= 0 else "-", math.fabs(self.a[i]))

    def power(self):
        if any(self.xy[1, :] <= 0) or any(self.xy[0, :] <= 0):
            return False
        xy_copy = self.xy.copy()
        self.xy = np.log(self.xy)
        self.polynomial(1)
        self.xy = xy_copy
        self.a = np.array([math.exp(self.a[1]), self.a[0]])
        self.f = lambda x: self.a[0] * x**self.a[1]
        self.x_aprx = np.array([self.a[0] * self.xy[0, i]**self.a[1] for i in range(0, self.n)])
        self.f_str = "{}x^{:.3f}".format("" if self.a[0] == 1 else "{:.3f}".format(self.a[0]), self.a[1])
        return True

    def exp(self):
        if any(self.xy[1, :] <= 0):
            return False
        xy_copy = self.xy.copy()
        self.xy = np.vstack([self.xy[0, :], np.log(self.xy[1, :])])
        self.polynomial(1)
        self.xy = xy_copy
        self.a = np.array([math.exp(self.a[1]), self.a[0]])
        self.f = lambda x: self.a[0] * math.exp(self.a[1] * x)
        self.x_aprx = np.array([self.a[0] * math.exp(self.a[1] * self.xy[0, i]) for i in range(0, self.n)])
        self.f_str = "{}e^({:.3f}x)".format("" if self.a[0] == 1 else "{:.3f}".format(self.a[0]), self.a[1])
        return True

    def log(self):
        if any(self.xy[0, :] <= 0):
            return False
        xy_copy = self.xy.copy()
        self.xy = np.vstack([np.log(self.xy[0, :]), self.xy[1, :]])
        self.polynomial(1)
        self.xy = xy_copy
        self.a = np.array([self.a[1], self.a[0]])
        self.f = lambda x: self.a[1] * math.log(x) + self.a[0]
        self.x_aprx = np.array([self.a[1] * math.log(self.xy[0, i]) + self.a[0] for i in range(0, self.n)])
        self.f_str = "{}ln(x) {} {:.3f}".format("" if self.a[1] == 1 else "{:.3f}".format(self.a[1]), "+" if self.a[0] >= 0 else "-", math.fabs(self.a[0]))
        return True

    def S(self):
        return ((self.x_aprx - self.xy[1, :])**2).sum()

    def stddev(self):
        return math.sqrt(self.S() / self.n)

    def R2(self):
        return 1 - self.S() / ((self.x_aprx**2).sum() - self.x_aprx.sum()**2 / self.n)

def pearson_corr_coef(xy: np.ndarray) -> float:
    x_avg = xy[0, :].mean()
    y_avg = xy[1, :].mean()
    n = xy.shape[1]
    return sum([(xy[0, i] - x_avg) * (xy[1, i] - y_avg) for i in range(0, n)]) / \
        math.sqrt(sum([(xy[0, i] - x_avg)**2 for i in range(0, n)]) * sum([(xy[1, i] - y_avg)**2 for i in range(0, n)]))
