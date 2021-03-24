public class ForStatement {
    public void forStatementAnalyze() {
        Integer[] a = {2, 3, 4, 5};
        int j = 0;
        for(int i = 0; 1 + i <= 3; ++i) {
            a += 1;
        }
        for(Integer x : a) {
            a += 2;
        }
        for(;;) {}
    }
}
