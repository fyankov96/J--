package pass;

public class ForStatement {
    public void compute() {
        int[] a = {2, 3, 4, 5};
        int b, c;
        int res = 0;
        
        for(int i = 0, k = 1; 1 + i <= 3; ++i) {
            res += a[k];
        }
        
        for(b = 0, c = 0; c <= 3 && b <= 3; b++) {}

        //Could add support for this
        //for(java.lang.Integer x = 1; res <= 2; res++) {}
        
        for(;;) {}
    }
}
