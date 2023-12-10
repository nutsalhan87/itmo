import random
import math
from typing import Dict
import pandas as pd
import numpy as np


def distance(a: np.ndarray, b: np.ndarray) -> float:
    return math.sqrt(((a - b) ** 2).sum())


def initialize_clusters(data: pd.DataFrame, k: int) -> Dict[int, pd.Series]:
    cluster_centroids: Dict[int, pd.Series] = {0: data.sample().iloc[0]}
    for i in range(1, k):
        row_distances: Dict[int, float] = {}
        for row_idx, row in data.iterrows():
            centroid_distances = []
            for (cluster_idx, cluster_center_coords) in cluster_centroids.items():
                centroid_distances.append(
                    distance(row.to_numpy(), cluster_center_coords.to_numpy()) ** 2,
                )
            row_distances[int(row_idx)] = min(centroid_distances)
        sum_dist = sum(row_distances.values())
        rnd = random.random() * sum_dist
        temp_sum = 0
        for (row_idx, dist) in row_distances.items():
            temp_sum += dist
            if temp_sum > rnd:
                cluster_centroids[i] = data.loc[row_idx]
                break
    return cluster_centroids


def intracluster_distance(centroids: Dict[int, pd.Series], clusters: Dict[int, list[pd.Series]]) -> float:
    sums_distance = []
    for (cluster_idx, centroid) in centroids.items():
        sums_distance.append(0)
        for row in clusters[cluster_idx]:
            sums_distance[-1] += distance(row.to_numpy(), centroid.to_numpy())
        sums_distance[-1] /= len(clusters[cluster_idx])
    return sum(sums_distance)


def cluster_centroids(data: pd.DataFrame, k: int) -> Dict[int, pd.Series]:
    cluster_centroids: Dict[int, pd.Series] = initialize_clusters(data, k)
    while True:
        clusters = {cluster_idx: [] for cluster_idx in cluster_centroids.keys()}
        for _, row in data.iterrows():
            centroid_distances = []
            for (cluster_idx, cluster_center_coords) in cluster_centroids.items():
                centroid_distances.append(
                    (
                        cluster_idx,
                        distance(row.to_numpy(), cluster_center_coords.to_numpy()) ** 2,
                    )
                )
            cluster_idx = int(min(centroid_distances, key=lambda x: x[1])[0])
            clusters[cluster_idx].append(row)
        under_intracluster_distance = intracluster_distance(cluster_centroids, clusters)
        for (cluster_idx, cluster_rows) in clusters.items():
            if len(cluster_rows) != 0:
                cluster_centroids[cluster_idx] = pd.DataFrame(cluster_rows).mean()
        post_intracluster_distance = intracluster_distance(cluster_centroids, clusters)
        if math.fabs(post_intracluster_distance - under_intracluster_distance) < 1e-3:
            break
    return cluster_centroids


def predict(data: pd.DataFrame, centroids: Dict[int, pd.Series]) -> list[int]:
    prediction: list[int] = []
    for _, row in data.iterrows():
        centroid_distances = []
        for (cluster_idx, cluster_center_coords) in centroids.items():
            centroid_distances.append(
                (
                    cluster_idx,
                    distance(row.to_numpy(), cluster_center_coords.to_numpy()) ** 2,
                )
            )
        cluster_idx = int(min(centroid_distances, key=lambda x: x[1])[0])
        prediction.append(cluster_idx)
    return prediction
