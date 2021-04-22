package pass;


interface Interf {
    static int d = 4;

    int e = 5;
    
    int a();
}

interface SubInterf extends pass.Interf {
    int b();
}

/*
class InterfaceTest implements SubInterf  {
    int test = 1;
    void b(int a) {

    }

    void a(int a) {

    }

    int a() {
        return 1;
    }


    int b() {
        return 2;
    }

}*/

class SubClass implements Interf {
    int c = 4;



    public int a() {
        return 2;
    }

}

