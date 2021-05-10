// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package pass;
import java.lang.System;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.IndexOutOfBoundsException;

public class ExceptionHandling {

    public int tryCatch() {
        int x = 0;
        try {
            x = 42;
            throw new IndexOutOfBoundsException();
        } catch (NullPointerException e) {
            x = 2+3;
        } catch (IllegalArgumentException i) {
            x = 3-1;
        } finally {
            System.out.println("done"); 
        }

        try {
            throw new IndexOutOfBoundsException();
        } catch (IndexOutOfBoundsException e) {
            x = 2+6;
        }
        return x;
    }

    public void throwUnexpected() throws IndexOutOfBoundsException {
        throw new IllegalArgumentException();
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
        } catch (IndexOutOfBoundsException e) {
            c = "Caught";
        }
        return c;
    }

    public String illegalArgument(String f) {
        
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
    void bar(String a) {
        if(a == "Illegal") {
            throw new IllegalArgumentException();
        }
    }
}
