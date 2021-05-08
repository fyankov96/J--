package pass;

public class DivisionAssignment {
    public int divideAssign(int x, int y) {
        int z = 42;
        z /= 314;
        return x /= y;
    }

    public double divideAssignDouble(double x, double y) {
        int z = 3.14;
        z /= 15.92;
        return x /= y;
    }
}