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
}
