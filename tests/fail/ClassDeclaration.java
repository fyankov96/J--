package fail;

public class ClassDeclaration {
    int a = 1;
    private static int b = 2;

    public ClassDeclaration() {}

    public ClassDeclaration(int a) {
        this.a = a;
    }
    
    public static int getA() {
        return a;
    }
    
    void setB(int b) {
        this.b = b;
    }
}
