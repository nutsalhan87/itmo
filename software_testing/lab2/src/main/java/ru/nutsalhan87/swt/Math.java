package ru.nutsalhan87.swt;

public class Math {
    private static final Pow powF = new Pow();
    private static final Sin sinF = new Sin();
    private static final Cos cosF = new Cos();
    private static final Ln lnF = new Ln();
    private static final Log logF = new Log();

    public static double pow(double x, int n) {
        return powF.apply(x, n);
    }

    public static double sin(double x) {
        return sinF.apply(x);
    }

    public static double cos(double x) {
        return cosF.apply(x);
    }

    public static double ln(double x) {
        return lnF.apply(x);
    }

    public static double log(double x, double n) {
        return logF.apply(x, n);
    }
}
