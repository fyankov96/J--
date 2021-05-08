/* This test aims to test if the codegen of doubles work properly */
package pass;


public class DoubleFields {
    double f1, f2, f3;

    public DoubleFields() {
        f1 = 0.0;
        f2 = 1.0;
        f3 = f1 + f2;
    }

    public DoubleFields(double f1, double f2) {
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = this.f1 + f2;
    }

    public double getF1(){
        return this.f1;
    }

    public double getF2(){
        return this.f2;
    }

    public double getF3(){
        return this.f3;
    }

    public boolean greaterThanF1(double other){
        return other > f1;
    }

    public void sumF1(double val) {
        f1 += val;
        f3 = f1 + f2;
    }

    public void mulF2(double val) {
        f2 *= val;
        f3 = f1 + f2;
    }
}