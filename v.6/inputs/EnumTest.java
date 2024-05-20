//class A{
//	int a;
//	
//    A(int a){
//    	this.a = a;
//    }
//    
//    @Override
//    public boolean equals(Object o)
//	{
//		if (this == o)
//			return true;
//		
//		return false;
//		
//	}
//}

class B{
	
	public enum Methodology {
        ITERATE, CONVERGE;
    }
	
	protected Methodology m;
	int x;

	
	B(int x_){
		x = x_;
	}

//	public Methodology getMethodology(int opt) {
//		return opt==0? Methodology.ITERATE: Methodology.CONVERGE;
//	}

}

public class EnumTest
{
//    private static A parser = new A(8);
//    private static A parser1 = new A(8);
//    private int x;
//    
//    EnumTest(int x){
//    	this.x = x;
//    }
//    
//    @Override
//	public boolean equals(Object o)
//	{
//		if (this == o)
//			return true;
//		return false;
//		
//	}
	
	public static void main(String[] args) {

		B b = new B(7);
		b.m = B.Methodology.ITERATE;
		
		B bb = new B(7);
		System.out.println("b- "+ b.equals(bb));
		System.out.println(b.m);

		
	}
}