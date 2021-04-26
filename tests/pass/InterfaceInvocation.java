public interface InterfaceA {
    int method1();
    String method2(int l);
}

public interface InterfaceB extends InterfaceA {
    int method3();
}

public class Impl1 extends InterfaceB {
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

public class Impl2 extends InterfaceB {
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