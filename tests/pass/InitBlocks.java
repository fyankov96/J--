package pass;

class MyClass {
    static int x = 0;
    int y = 0;
}

public class InitBlocks extends MyClass {
    public int a = 1;
    public static int b = 1;
    public int c = 3;
    double x = 3.14;

    public InitBlocks() {}

    public InitBlocks(int a) {
        this();
        this.a = a;
    }

    {
        a = 3;
    }

    {
        this.a = b;
        c = super.y;
    }
    
    static {
        b = 2;
    }

    {
        x = 0.0;
    }

    {
        int x = 0;
    }

    {
        int a = 3;
    }        
}
