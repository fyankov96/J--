package pass;

public class ClassDeclaration {
    int a = 5;
    private static int b = 1;
    
    double x = 3.14;
    {
        int x = 0;
        a = 3;
    }

    {
        x = 0.0;
        a = 3;
    }

    static {
        b = 2;
    }
    
    /*
    public static int getB() {
        return b;
    }
    
    void setA(int a) {
        this.a = a;
    }
    */
    
}
