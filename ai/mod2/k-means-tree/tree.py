import random
import math
from typing import Dict, Any
from statistics import mode
import kmeans

import pandas as pd
import numpy as np


def info(data: pd.DataFrame) -> float:
    partials: list[float] = []
    for (cluster_idx, cluster) in data.groupby('cluster'):
        freq = cluster.size / data.size
        partials.append(freq * math.log2(freq))
    return -1 * sum(partials)


def info_attr(data: pd.DataFrame, attr: Any) -> float:
    partials: list[float] = []
    for (_, subdata) in data.groupby(attr):
        partials.append(subdata.size / data.size * info(subdata))
    return sum(partials)


def split(data: pd.DataFrame, attr: Any) -> float:
    partials: list[float] = []
    for (_, subdata) in data.groupby(attr):
        freq = subdata.size / data.size
        partials.append(freq * math.log2(freq))
    return -1 * sum(partials)


def gain(data: pd.DataFrame, attr: Any) -> float:
    return (info(data) - info_attr(data, attr)) / split(data, attr)


class Node:
    child_attr: Any
    nodes: Dict[Any, 'Node']
    cluster: Any

    def __init__(self):
        self.child_attr = None
        self.nodes = {}
        self.cluster = None


def create_node(data: pd.DataFrame, unique_attrs: Dict[Any, list[Any]], current_depth: int, max_depth: int = None) -> Node:
    node = Node()
    if data.columns.size == 1:
        node.cluster = data['cluster'].mode().iloc[0]
        return node
    if data['cluster'].nunique() == 1:
        node.cluster = data['cluster'].iloc[0]
        return node
    gains: Dict[Any, float] = {attr: gain(data, attr) for attr in data.drop('cluster', axis=1).columns}
    node.child_attr = max(gains, key=gains.get)
    if max_depth is not None and current_depth > max_depth:
        node.cluster = data['cluster'].mode().iloc[0]
        return node
    for attr_value in unique_attrs[node.child_attr]:
        attr_group = data.loc[data[node.child_attr] == attr_value]
        if attr_group.size == 0:
            child = Node()
            child.cluster = data['cluster'].mode().iloc[0]
            node.nodes[attr_value] = child
        else:
            node.nodes[attr_value] = create_node(attr_group.drop(node.child_attr, axis=1), unique_attrs, current_depth + 1, max_depth)
    node.cluster = mode([child.cluster for child in node.nodes.values()])
    return node


class ClusterTree:
    root: Node

    def __init__(self, data: pd.DataFrame, k: int, max_depth: int = None):
        """DataFrame can't have a column called 'cluster'.\n
        Data must be a finite set of attributes"""
        centroids: Dict[int, pd.Series] = kmeans.cluster_centroids(data, k)
        data_clustered = data.copy()
        predicted = kmeans.predict(data, centroids)
        data_clustered['cluster'] = pd.Series(predicted)
        unique_attrs: Dict[Any, list[Any]] = {attr: data[attr].unique().tolist() for attr in data.columns}
        self.root = create_node(data_clustered, unique_attrs, 1, max_depth)

    def predict(self, data: pd.DataFrame) -> list[float]:
        predicted: list[float] = []
        for (_, row) in data.iterrows():
            node = self.root
            while node.child_attr is not None and row[node.child_attr] in node.nodes:
                node = node.nodes[row[node.child_attr]]
            predicted.append(node.cluster)
        return predicted