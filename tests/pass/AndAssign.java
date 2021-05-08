package pass;

public class AndAssign {
    
    public int andAssign(int a, int b){
        boolean x = true;
        x &= false;

        b &= a;

        return b;
    }    
}
