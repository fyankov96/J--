public class ForStatement {
    public void forStatementAnalyze() {
        Integer[] a = {2, 3, 4, 5};
        int j = 0;
        int i = 0;
        for(Integer x : a) {
            a = a + 2;
        }
        for(int i = 0; 1 + i <= 3; i++) {
            a = a + 1;
        }

        for(;;) {}
    }
}
