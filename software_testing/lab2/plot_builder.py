import os
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

csv_dir = "out_csv"
out_csv = os.listdir(csv_dir)
if not os.path.isdir("plots"):
    os.mkdir("plots")
for idx, filename in enumerate(out_csv):
    data = pd.read_csv(csv_dir + "/" + filename)
    for col in data.columns:
        data[col] = pd.to_numeric(data[col], errors='coerce')
    ax = data.plot(x=data.columns[0], y=data.columns[1], ylim=(-5, 5), grid=True)
    fig = ax.get_figure()
    fig.savefig("plots/" + filename.replace("csv", "png"))