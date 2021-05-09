package fail;

public class Constants {
    final int x = 1;
    final int y;

    {
        x = 2;
        y = 3;
    }

    {
        y = 4;
    }

    public Constants(){
        x = 1;
        y = 2;
    }

    public void method() {
        final int a = 1;
        final int b;
        a = 2;
        b = 3;
        b = 4;

        y = 1;
        x = 2;

        int[] arr = {2, 3};
        int res = 0;
        for(final int x : arr) {
            x = res;
        }
    }
}