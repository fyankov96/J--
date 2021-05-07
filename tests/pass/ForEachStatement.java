package pass;


public class ForEachStatement {
    public void forEachStatementAnalyze() {
        int[] a = {2, 3};
        int res = 0;

        for(int x : a) {
            res += x;
        }
    }
}