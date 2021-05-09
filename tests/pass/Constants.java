package pass;


public class Constants {
    public final int x = 1;
    public final int y;
    int z = 2;

    public final String str;
    public final double d;
    
    {
        y = 3;
        str = "string";
        d = 3.14;
    }

    public int arrayLiteral() {
        int[] arr = {2, 3}; 
        return arr[0];
    }

    public int method() {
        final int a;
        final int b = 1;

        a = 2;
        z = 3;
        
        final double d = 3.14;
        final String s = "constant";
        
        int[] arr = {2, 3};
        int res = 0;
        /*
        for(final int i : arr) {
            res += i;
        }*/

        return a + b + z;
    }
}
