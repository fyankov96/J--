// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package pass;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.IndexOutOfBoundsException;

public class ExceptionHandling {
    public ExceptionHandling() throws IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException();
    }

    public int tryCatch() throws IllegalArgumentException {
        try {
            int x = 42;
            throw new NullPointerException();
        } catch (NullPointerException e) {
            return 0;
        } catch (IllegalArgumentException i) {
            return 2;
        }
        finally {return 1;}  
    }
}
