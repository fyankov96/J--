package pass;

public class ForStatement {
    public int compute() {
        int[] a = {2, 3, 4, 5};
        int b, c;
        int res = 0;
        
        for(int i = 0; i <= a.length-1; i++) {
            res += a[i]; //14
        }

        for(int i = 0, k = 1; i < 2; i++) {
            res += k; //16
        }

        for(b = 0; b <= 3; b++) {
            res += b; //22
        }

        for(b = 0, c = 0; c <= 3 && b <= 3; b++) {}
        
        //for(;;) {}

        return res;
    }
}
