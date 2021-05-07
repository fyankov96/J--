package pass;

public interface InterfaceA {
    int method1();
    String method2(int l);
    int method3();
}

public class InterfaceTestImpl1 implements InterfaceA {
    public int method1() {
        return 1;
    }

    public String method2(int l) {
        String a = ">";
        return a.concat(toString());
    }

    public int method3() {
        return 3;
    }
}

public class InterfaceTestImpl2 implements InterfaceA {
    public int method1() {
        return -1;
    }

    public String method2(int l) {
        String a = "<";
        return a.concat(toString());
    }

    public int method3() {
        return -3;
    }
}

public class InterfaceTestImpl3 extends InterfaceTestImpl2 {
    public int method1() {
        return -11;
    }
}
