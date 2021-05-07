package pass;


public class Constants {
    final int x = 1;
    final int y;
    int z = 2;
    
    {
        y = 3;
    }

    public void method() {
        final int a;
        final int b = 1;

        a = 2;
        z = 3;
        
        final double d = 3.14;
        final String s = "constant";
        
        int[] arr = {2, 3};
        int res = 0;
        for(final int i : arr) {
            res += i;
        }
    }
}