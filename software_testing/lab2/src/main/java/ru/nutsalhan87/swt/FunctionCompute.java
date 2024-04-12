package ru.nutsalhan87.swt;

import java.util.function.Function;

public class FunctionCompute implements Function<Double, Double> {
    
    private final Math math;

    public FunctionCompute(Math math) {
        this.math = math;
    }

    //x <= 0 : ((tan(x) - csc(x)) * sec(x))
    //x > 0 : (((((log_5(x) / ln(x)) + log_3(x)) / log_3(x)) ^ 3) + (log_3(x) - (log_5(x) / ln(x))))

    @Override
    public Double apply(Double x){
        if(x<=0){
            return (math.tan(x) - math.csc(x))*math.sec(x);
        }
        else{
            return math.pow((((math.log(x, 5)/math.ln(x))+math.log(x, 3))/math.log(x, 3)), 3) + (math.log(x, 3) - (math.log(x, 5)/math.ln(x)));
        }
    }


}
