package fail;

public class ForEachStatement {
    public void forEachStatementAnalyze() {
        int[] i_arr = {2, 3, 4, 5};
        char[] c_arr = {'a', 'b', 'c'};
        int not_arr = 2;

        int res = 0;
        
        //Parse errors
        
        //for(x : i_arr) {}
        //for(x : ) {}
        //for( : ) {}
        
        //Type errors
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
