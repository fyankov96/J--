package pass;

public class MultiplicationAssignment {
    public int multiplyAssign(int x, int y) {
        double a = 42;
        a *= 3;
        return x *= y;
    }

    public double multiplyAssignDouble(double x, double y) {
        double a = 3.14;
        a *= 15.92;
        return x *= y;
    }
}