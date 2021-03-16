// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package pass;

import java.lang.System;

public class ExceptionHandling {

    public ExceptionHandling() throws NullPointerException {
        throw new NullPointerException();
    }

    public void Throwing() throws NullPointerException {
        throw new NullPointerException();
    }

    public int TryCatch() {
        try {
            int x = 42;
            throw new NullPointerException();
        } catch (NullPointerException e) {
            System.out.println("Caught an error");
            return 0;
        }
        finally {return 1;}  
    }
}
