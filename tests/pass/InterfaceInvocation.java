package pass;

public interface InterfaceA {
    int method1();
    String method2(int l);
}

public interface InterfaceB extends pass.InterfaceA {
    int method3();
}

public class Impl1 implements InterfaceB {
    int method1() {
        return 1;
    }

    String method2(int l) {
        String a = ">";
        return a.concat(toString());
    }

    int method3() {
        return 3;
    }
}

public class Impl2 implements InterfaceB {
    int method1() {
        return -1;
    }

    String method2(int l) {
        String a = "<";
        return a.concat(toString());
    }

    int method3() {
        return -3;
    }
}


public class Impl3 extends Impl2 {
    int method1() {
        return -11;
    }
}