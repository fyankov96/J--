package pass;

public class AndAssign {
    /**
     * Assigns a to b and returns the value of b
     * @param a 
     * @param b
     * @return
     */
    public int andAssign(int a, int b){
        b &= a;
        return b;
    }    
}
