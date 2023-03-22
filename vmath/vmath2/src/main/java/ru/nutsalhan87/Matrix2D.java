package ru.nutsalhan87;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix2D {
    public double[][] matrix;

    public double[] solve() {
        if (matrix.length != 2 || matrix[0].length != 3 || matrix[1].length != 3) {
            return null;
        }

        double[][] copy = {{}, {}};
        copy[0] = Arrays.stream(matrix[0]).toArray();
        copy[1] = Arrays.stream(matrix[1]).toArray();
        double da = - matrix[1][0] / matrix[0][0];
        copy[1][0] += da * copy[0][0];
        copy[1][1] += da * copy[0][1];
        copy[1][2] += da * copy[0][2];
        double[] answer = {0, 0};
        answer[1] = copy[1][2] / copy[1][1];
        answer[0] = (copy[0][2] - copy[0][1] * answer[1]) / copy[0][0];

        return answer;
    }
}
