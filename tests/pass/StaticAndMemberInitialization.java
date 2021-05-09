package pass;

public class StaticAndMemberInitialization {
    public int a = 1;
    public static int b = 2;    

    {
        a = 3;
    }

    static 
    {
        b = 3;
    }
}
