package fail;

import java.lang.System;

public class OrAssign {
    public static void main(String[] args){
        boolean a = true;
        a |= "abc";
        System.out.println(a);
    }
}
