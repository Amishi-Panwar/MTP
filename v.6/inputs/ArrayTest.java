 class A{
	int x[];
	
	A(int n){
		this.x= new int[n];
	}
}

class B extends A{
	int a;
	
	B(int p, int n) {
	    super(n);
		this.a = p;
	}
}
 
public class ArrayTest{
	public static void main(String args[]) {
		A a = new A(5);
		A aa = new A(5);
		System.out.println(a.equals(aa));
		System.out.println(a.x[0]);
		
		B b = new B(2,4);
		B bb = new B(2,4);
		System.out.println(b.equals(bb));
	}
}