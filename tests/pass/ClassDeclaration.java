package pass;

public class ClassDeclaration {
    int a = 5;
    private static int b = 1;
    
    {
        int x = 0;
        a = 3;
    }

    public static int getB() {
        return b;
    }

    void setA(int a) {
        this.a = a;
    }

    static {
        b = 2;
    }
    
    static {
        b = 3;
    }
}
