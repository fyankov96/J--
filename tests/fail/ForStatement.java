package fail;

public class ForStatement {
    public void forStatementAnalyze() {
        int[] i_arr = {2, 3, 4, 5};
        char[] c_arr = {'a', 'b', 'c'};
        int not_arr = 2;

        int res = 0;
        
        //Parse errors
        //for(i = 0; int i = 2; ++i) {}
        //for(;) {}
        //for(;;;) {}
        
        //Type errors
        for(i = 0; i <= 2; ++i) {}

        for(int i = 0; i = 2; ++i) {}

        for(int i = 0; i < 2; ++i) {}
        i = 3;
        
        for(int i = 0; i <= 2; i++) {
            int res = 2;
            not_arr += 1;
        }

        int i = 0;
        for(int i = 0; i <= 2; i++) {
            not_arr += 1;
        }
    }
}
