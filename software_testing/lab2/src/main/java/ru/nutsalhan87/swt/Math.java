package ru.nutsalhan87.swt;

public class Math {
    private final Pow powF;
    private final Sin sinF;
    private final Cos cosF;
    private final Ln lnF;
    private final Log logF;
    private final Tan tanF;
    private final Csc cscF;
    private final Sec secF;

    public Math(Pow powF, Sin sinF, Cos cosF, Ln lnF, Log logF, Tan tanF, Csc cscF, Sec secF) {
        this.powF = powF;
        this.sinF = sinF;
        this.cosF = cosF;
        this.lnF = lnF;
        this.logF = logF;
        this.tanF = tanF;
        this.cscF = cscF;
        this.secF = secF;
    }


    public  double pow(double x, int n) {
        return powF.apply(x, n);
    }

    public  double sin(double x) {
        return sinF.apply(x);
    }

    public  double cos(double x) {
        return cosF.apply(x);
    }

    public  double ln(double x) {
        return lnF.apply(x);
    }

    public  double log(double x, double n) {
        return logF.apply(x, n);
    }

    public  double tan(double x){ return tanF.apply(x); }

    public  double csc(double x){ return cscF.apply(x); }

    public   double sec(double x){ return secF.apply(x); }
}
