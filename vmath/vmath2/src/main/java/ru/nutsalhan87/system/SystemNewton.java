package ru.nutsalhan87.system;

import ru.nutsalhan87.Matrix2D;
import ru.nutsalhan87.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class SystemNewton implements SystemSolve {
    @Override
    public Map<String, Double> solve(double x1, double y1, double precision,
                                     BiFunction<Double, Double, Double> functionF, BiFunction<Double, Double, Double> functionS) {
        BiFunction<Double, Double, Double> functionFdx = Util.getDerivative(functionF, 1, 0);
        BiFunction<Double, Double, Double> functionFdy = Util.getDerivative(functionF, 0, 1);
        BiFunction<Double, Double, Double> functionSdx = Util.getDerivative(functionS, 1, 0);
        BiFunction<Double, Double, Double> functionSdy = Util.getDerivative(functionS, 0, 1);

        double dx;
        double dy;
        double x0;
        double y0;
        Matrix2D matrix2D = new Matrix2D();
        double[] dxdy;
        int iterations = 0;
        do {
            x0 = x1;
            y0 = y1;
            matrix2D.matrix = new double[][]{{functionFdx.apply(x1, y1), functionFdy.apply(x1, y1), -1 * functionF.apply(x1, y1)},
                    {functionSdx.apply(x1, y1), functionSdy.apply(x1, y1), -1 * functionS.apply(x1, y1)}};
            dxdy = matrix2D.solve();
            dx = dxdy[0];
            dy = dxdy[1];
            x1 = x0 + dx;
            y1 = y0 + dy;
            ++iterations;
        } while (Math.abs(x1 - x0) > precision || Math.abs(y1 - y0) > precision);

        Map<String, Double> answer = new HashMap<>();
        answer.putIfAbsent("x", x1);
        answer.putIfAbsent("y", y1);
        answer.putIfAbsent("iters", (double)iterations);
        answer.putIfAbsent("dx", dx);
        answer.putIfAbsent("dy", dy);

        return answer;
    }
}
