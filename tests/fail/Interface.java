package pass;


interface Interf {
    void a();
}

interface SubInterf extends pass.Interf {
    void b();
}


class InterfaceTest implements SubInterf  {
    int test = 1;
    void b(int a) {}
}
