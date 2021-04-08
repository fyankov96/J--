package pass;

public class ForStatement {
    public void compute() {
        int[] a = {2, 3, 4, 5};
        int b, c;
        int res = 0;
        // For-step loop tests
        for(int i = 0, k = 1; 1 + i <= 3; ++i) {
            res += a[k];
        }
        
        for(b = 0, c = 0; c <= 3 && b <= 3; b++) {}
        
        // For-each loop tests    
        for(int x : a) {
            res += x;
        }
        


        for(;;) {}
    }
}
