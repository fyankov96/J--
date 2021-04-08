package fail;

public class ForStatement {
    public void forStatementAnalyze() {
        int[] i_arr = {2, 3, 4, 5};
        char[] c_arr = {'a', 'b', 'c'};
        int not_arr = 2;

        int res = 0;
        
        // For-step loop tests
        for(int i = 0; i = 2; ++i) {
            not_arr += 1;
        }
        
        for(int i = 0; i <= 2; i++) {
            int res = 2;
            not_arr += 1;
        }

        int i = 0;
        for(int i = 0; i <= 2; i++) {
            not_arr += 1;
        }

        
        
        //for(;) {}

        //for(;;)

        //for(;;;) {}


        // For-each loop tests    
        /*
        for(x : i_arr) {
            res += 2;
        }
        */

        for(int x : not_arr) {
            res += x;
        }

        for(int x : c_arr) {
            res += x;
        }
        
        int x = 2;
        for(int x : i_arr) {
            res += x;
        }
    }
}
