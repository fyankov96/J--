package pass;

public class ForEachStatement {
    public void forEachStatementAnalyze() {
        int[] a = {2, 3};
        int res = 0;

        for(int x : a) {
            res += x;
        }

        //MyIterable b = new MyIterable();
        //for(int x : b) {}
    }
}

//public class MyIterable implements java.lang.Iterable {}
