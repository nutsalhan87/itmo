import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.metrics import r2_score

def decoder(s):
    if s == 'Yes':
        return 1.
    else:
        return 0.

class LeastSquares:
    def __init__(self, x: np.ndarray, y: np.ndarray):
        x = x.copy()
        if x.ndim == 1:
            ones = np.zeros(x.shape[0]) + 1
            x = np.column_stack((ones, x))
        else:
            np.insert(x, 0, 1, 1)
        self.w = np.linalg.inv(x.T @ x) @ x.T @ y
    
    def fit(self, x: np.ndarray) -> np.ndarray:
        x = x.copy()
        if x.ndim == 1:
            ones = np.zeros(x.shape[0]) + 1
            x = np.column_stack((ones, x))
        else:
            np.insert(x, 0, 1, 1)
        return x @ self.w

def least_squares(x: np.ndarray, y: np.ndarray) -> np.ndarray:
    return np.linalg.inv(x.T @ x) @ x.T @ y

data = pd.read_csv('Student_Performance.csv')
data['Extracurricular Activities'] = data['Extracurricular Activities'].apply(lambda x: decoder(x))
x = data.drop('Performance Index', axis=1)
y = data['Performance Index']
print(f"Info:\n{x.info()}\n")
print(f"Mean:\n{x.mean()}\n")
print(f"Std:\n{x.std()}\n")
print(f"Min:\n{x.min()}\n")
print(f"Max:\n{x.max()}\n")

x = (x - x.min()) / (x.max() - x.min())
x_train, x_test, y_train, y_test = train_test_split(x, y, train_size=0.8, random_state=451)
b = LeastSquares(x_train.to_numpy(), y_train.to_numpy())
print(r2_score(y_test, b.fit(x_test)))

for column in x.columns:
    x2 = x[column]
    x2_train, x2_test, y2_train, y2_test = train_test_split(x2, y, train_size=0.8, random_state=451)
    b2 = LeastSquares(x2_train.to_numpy(), y2_train.to_numpy())
    print(f"{column}: {r2_score(y2_test, b2.fit(x2_test))}")

x3 = x.drop(['Extracurricular Activities', 'Sleep Hours', 'Sample Question Papers Practiced'], axis=1)
x3_train, x3_test, y3_train, y3_test = train_test_split(x3, y, train_size=0.8, random_state=451)
b3 = LeastSquares(x3_train.to_numpy(), y3_train.to_numpy())
print(r2_score(y3_test, b3.fit(x3_test)))