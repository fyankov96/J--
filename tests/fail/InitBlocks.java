package fail;

import java.lang.NullPointerException;

class MyClass {
    static int x = 0;
    int y = 0;
}

public class InitBlocks extends MyClass{
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
        b = super.x;
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
