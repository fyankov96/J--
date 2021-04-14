// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package pass;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

public class ExceptionHandling {
    public ExceptionHandling() throws NullPointerException {
        throw new NullPointerException();
    }

    public int tryCatch() throws NullPointerException {
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
