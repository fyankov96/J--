package pass;

public class ForEachStatement {
    public void forEachStatementAnalyze() {
        int[] a = {2, 3, 4, 5};
        java.lang.Integer[] b = {2, 3, 4, 5}; 
        int res = 0;

        for(int x : a) {
            res += x;
        }

        //Could add support for this
        //for(java.lang.Integer x : b) {}
    }
}
