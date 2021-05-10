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
        throw new NullPointerException();
    }
    
    {
        throw new NullPointerException();
    }

    static {
        return 0;
    }
    
    {
        return 0;
    }

}
