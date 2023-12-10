import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import tree
from sklearn.model_selection import train_test_split
from sklearn.datasets import make_blobs, make_checkerboard

# x, y = make_blobs(n_samples=300, random_state=45, centers=4)
# data = pd.DataFrame(x, columns=['x', 'y'])

data = pd.DataFrame()
data['x'] = pd.Series([5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                       0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19])
data['y'] = pd.Series([10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                       9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5])

tr = tree.ClusterTree(data, 4)
data['cluster'] = pd.Series(tr.predict(data))
plt.figure(0)
for (cluster_idx, cluster) in data.groupby('cluster'):
    plt.scatter(cluster['x'], cluster['y'], color=np.random.rand(3,))
data.drop('cluster', axis=1, inplace=True)

train_x, test_x = train_test_split(data, train_size=0.8, random_state=451)
train_x.reset_index(inplace=True, drop=True)
test_x.reset_index(inplace=True, drop=True)
tr = tree.ClusterTree(train_x, 4, max_depth=1)

plt.figure(1)
train_x['cluster'] = pd.Series(tr.predict(train_x))
for (cluster_idx, cluster) in train_x.groupby('cluster'):
    plt.scatter(cluster['x'], cluster['y'], color=np.random.rand(3,))

plt.figure(2)
test_x['cluster'] = pd.Series(tr.predict(test_x))
for (cluster_idx, cluster) in test_x.groupby('cluster'):
    plt.scatter(cluster['x'], cluster['y'], color=np.random.rand(3,))

plt.show()