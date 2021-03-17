package pass;

public class classDeclaration {
    int a = 5;
    private static int b;
    {
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
