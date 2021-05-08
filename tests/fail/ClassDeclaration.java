package fail;

public class ClassDeclaration {
    int a = 5;
    static int b = 1;
    
    static {
        a = 2;
    }

    static {
        int c = a;
    }

    static {
        this.a = 2;
    }

    static {
        x = 0.0;
    }
    
}
