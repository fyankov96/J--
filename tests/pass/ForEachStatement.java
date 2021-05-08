package pass;


public class ForEachStatement {
    public int compute() {
        int[] a = {2, 3};
        int res = 0;

        for(int x : a) {
            res += x;
        }

        String[] b = {"a", "b"};
        for (String s : b) {}

        return res;
    }
}