package pass;

public class Conditional {
    public int cond(boolean a){
        int x = a ? 1 : 2;

        double y = (true && !(false)) ? 3.14 : 15.92;
        
        return x;
    }    
}
