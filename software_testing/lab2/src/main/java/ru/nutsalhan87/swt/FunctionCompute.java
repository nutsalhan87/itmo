package ru.nutsalhan87.swt;

import lombok.AllArgsConstructor;
import ru.nutsalhan87.swt.math.*;

@AllArgsConstructor
public class FunctionCompute implements Function {
    private Function pow3;
    private Function tg;
    private Function cosec;
    private Function sec;
    private Function ln;
    private Function log3;
    private Function log5;

    // x <= 0 : ((tan(x) - csc(x)) * sec(x))
    // x > 0 : (((((log_5(x) / ln(x)) + log_3(x)) / log_3(x)) ^ 3) + (log_3(x) - (log_5(x) / ln(x))))
    @Override
    public Double apply(Double x) {
        if (x <= 0) {
            var tgRes = tg.apply(x);
            var cosecRes = cosec.apply(x);
            var secRes = sec.apply(x);
            var s1 = tgRes - cosecRes;
            var ans = s1 * secRes;
            return ans;
        } else {
            var lnRes = ln.apply(x);
            var log3Res = log3.apply(x);
            var log5Res = log5.apply(x);
            var rel = log5Res / lnRes;

            double p1;
            {
                var s1 = rel;
                var s2 = s1 + log3Res;
                var s3 = s2 / log3Res;
                p1 = pow3.apply(s3);
            }
            double p2 = log3Res;
            double p3 = -rel;
            double ans = p1 + p2 + p3;
            return ans;
        }
    }
}