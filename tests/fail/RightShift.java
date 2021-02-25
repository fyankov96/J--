package fail;

import java.lang.System;

public class RightShift {
    public static void main(String[] args) {
        System.out.println('a' >> 42);
        System.out.println("Hello!" >> 12);
        System.out.println(12 >> 'a');
        System.out.println(12 >> -1.2);
    }
}
