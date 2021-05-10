package pass;

class MyClass {
    static int x = 0;
    int y = 0;
}

public class ClassDeclaration extends MyClass {
    int a = 1;
    private static int b = 2;

    public ClassDeclaration() {}

    public ClassDeclaration(int a) {
        this.a = a;
    }
    
    double x = 3.14;
    {
        x = 0.0;
    }

    {
        int x = 0;
    }

    {
        int a = 3;
    }

    {
        a = 3;
    }

    {
        this.a = b;
    }

    {
        a = super.y;
    }

    static {
        b = 2;
    }
    
    public static int getB() {
        return b;
    }
    
    void setA(int a) {
        this.a = a;
    }
    
}
