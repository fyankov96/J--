package pass;

public class ClassDeclaration {
    int a;
    private static int b;

    public ClassDeclaration() {}

    public ClassDeclaration(int a) {
        this.a = a;
    }
    
    //double x = 3.14;
    {
        //x = 0.0;
        int x = 0;
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
