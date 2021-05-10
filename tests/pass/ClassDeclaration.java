package pass;

public class ClassDeclaration {
    public int a = 1;
    private static int b = 2;

    public ClassDeclaration() {}

    public ClassDeclaration(int a) {
        this.a = a;
    }
    
    public static int getB() {
        return b;
    }
    
    public void setA(int a) {
        this.a = a;
    }
    
}
