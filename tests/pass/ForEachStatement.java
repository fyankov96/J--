package pass;


public class ForEachStatement {

    public int sum() {
        int res = 0;
        int[] a = {2, 3, 4};
        for(int x : a) {
            res += x;
        }
        return res;
    }

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