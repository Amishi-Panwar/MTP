import java.util.Objects;
	void <init>()
	{
	    int temp$1;
	    Demographics this;
	    java.lang.String temp$0;
	
	    this := @this: Demographics;
	
	    specialinvoke this.<java.lang.Object: void <init>()>();
	
	    temp$0 = "Bhopal";
	
	    this.<Demographics: java.lang.String city> = temp$0;
	
	    temp$1 = 451111;
	
	    this.<Demographics: int pincode> = temp$1;
	
	    return;
	}

    static void <clinit>()
    {
        java.util.HashMap temp$0;

        temp$0 = new java.util.HashMap;

        specialinvoke temp$0.<java.util.HashMap: void <init>()>();

        <Demographics: java.util.HashMap map> = temp$0;

        return;
    }

    public boolean equals(java.lang.Object)
    {
        Demographics this;
        java.lang.Object o;
        boolean temp$0, temp$1;

        this := @this: Demographics;

        o := @parameter0: java.lang.Object;

        if this == o goto label1;

        goto label2;

     label1:
        nop;

        temp$0 = 1;

        return temp$0;

     label2:
        nop;

        temp$1 = 0;

        return temp$1;
    }

    
    public boolean equals(java.lang.Object)
    {
        this := @this: Demographics;

        o := @parameter0: java.lang.Object;

        if this == o goto label1;

        temp$1 = 0;

        ret temp$1;

     label1:
        temp$0 = 1;

        ret temp$0;
    }
    
    
    
    public static Demographics create_object()
    {
        java.lang.Integer temp$3, temp$6, temp$9, temp$20, temp$25;
        boolean temp$7;
        java.lang.Object[] temp$0;
        java.util.HashMap temp$5, temp$8, temp$19, temp$24;
        Demographics obj, temp$11, temp$12, temp$15, new_obj, temp$18, temp$22, new_obj, temp$23, temp$27;
        int hashvalue, temp$2, temp$4, temp$16, temp$17;
        java.lang.String temp$1, temp$13, temp$14;
        java.lang.Object temp$10, temp$21, temp$26;

        temp$0 = newarray (java.lang.Object)[2];

        temp$1 = <Demographics: java.lang.String city>;

        temp$0[0] = temp$1;

        temp$2 = <Demographics: int pincode>;

        temp$3 = staticinvoke <java.lang.Integer: java.lang.Integer valueOf(int)>(temp$2);

        temp$0[1] = temp$3;

        temp$4 = staticinvoke <java.util.Objects: int hash(java.lang.Object[])>(temp$0);

        hashvalue = temp$4;

        temp$5 = <Demographics: java.util.HashMap map>;

        temp$6 = staticinvoke <java.lang.Integer: java.lang.Integer valueOf(int)>(hashvalue);

        temp$7 = virtualinvoke temp$5.<java.util.HashMap: boolean containsKey(java.lang.Object)>(temp$6);

        if temp$7 == 0 goto label6;

        goto label1;

     label1:
        nop;

        temp$8 = <Demographics: java.util.HashMap map>;

        temp$9 = staticinvoke <java.lang.Integer: java.lang.Integer valueOf(int)>(hashvalue);

        temp$10 = virtualinvoke temp$8.<java.util.HashMap: java.lang.Object get(java.lang.Object)>(temp$9);

        temp$11 = (Demographics) temp$10;

        obj = temp$11;

        if obj == null goto label4;

        goto label2;

     label2:
        nop;

        temp$12 = obj;

        temp$13 = <Demographics: java.lang.String city>;

        temp$14 = <Demographics: java.lang.String city>;

        if temp$13 == temp$14 goto label3;

        goto label4;

     label3:
        nop;

        temp$15 = obj;

        temp$16 = <Demographics: int pincode>;

        temp$17 = <Demographics: int pincode>;

        if temp$16 == temp$17 goto label5;

        goto label4;

        goto label5;

        goto label5;

     label4:
        nop;

        temp$18 = new Demographics;

        specialinvoke temp$18.<Demographics: void <init>()>();

        new_obj = temp$18;

        temp$19 = <Demographics: java.util.HashMap map>;

        temp$20 = staticinvoke <java.lang.Integer: java.lang.Integer valueOf(int)>(hashvalue);

        temp$21 = virtualinvoke temp$19.<java.util.HashMap: java.lang.Object put(java.lang.Object,java.lang.Object)>(temp$20, new_obj);

        temp$22 = (Demographics) temp$21;

        return new_obj;

     label5:
        nop;

        return obj;

     label6:
        nop;

        temp$23 = new Demographics;

        specialinvoke temp$23.<Demographics: void <init>()>();

        new_obj = temp$23;

        temp$24 = <Demographics: java.util.HashMap map>;

        temp$25 = staticinvoke <java.lang.Integer: java.lang.Integer valueOf(int)>(hashvalue);

        temp$26 = virtualinvoke temp$24.<java.util.HashMap: java.lang.Object put(java.lang.Object,java.lang.Object)>(temp$25, new_obj);

        temp$27 = (Demographics) temp$26;

        return new_obj;
    }

class P1 {
	public static void main(String[] args) {

		 A x;
		 x = new A();

	}
}
//class B{
//	
//}
//	 
//class A extends B{
//	int f;
//		
//	A{
//		f = 3;
//	}
//	
//	@Override
//	public boolean equals(Object o)
//	{
//		if (this == o)
//			return true;
//		return false;
//	}
//
//	@Override
//	public String toString() {
//		return "f : " + this.f;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(this.f);
//	}
//}
//
