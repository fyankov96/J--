// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package pass;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.IndexOutOfBoundsException;
import java.lang.System;

public class ExceptionHandling {
    public ExceptionHandling() throws IllegalArgumentException {
        int x = tryCatch();
        System.out.println(x);
        throw new IndexOutOfBoundsException("out of bounds");
    }

    public int tryCatch() throws IndexOutOfBoundsException {
        int x = 0;
        try {
            x = 42;
            System.out.println(x);
        } catch (NullPointerException e) {
            x = 2+3;
        } catch (IllegalArgumentException i) {
            x = 3-1;
        } finally {
            System.out.println("done"); 
        }  
        return x;
    }

    public String divideByZero() {
        int a = 0;
        int b = 1;
        String c = "Init";
        try {
            b = b / a;
            c = "Not Caught";
        }
        catch (ArithmeticException e){
            c = "Caught";
        }
        return c;
    }

    public String illegalAccess() {
        int[] a = new int[3];
        String c;
        try {
            int b;
            b = a[5];
            c = "Not Caught";
        } catch (ArrayIndexOutOfBoundsException e) {
            c = "Caught";
        }
        return c;
    }

    public String illegalArgument(int f) {
        
        Foo foo = new Foo();
        String val = "Not Init";
        try{
            foo.bar(f);
            val = "Not Caught";
        } catch(IllegalArgumentException e) {
            val = "Caught";
        }
        return val;
    } 
}

class Foo {
    void bar(int a) {
        if(a == 0) {
            throw new IllegalArgumentException();
        }
    }
}
