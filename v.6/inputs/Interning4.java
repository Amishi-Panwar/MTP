import java.io.*;
import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;



public class Interning4 {
	
	public static void main(String [] args)
	{
//		Demographics.object_pool= new HashMap<Integer, APL>();
		String s ="Bhopal";
		
//		String s1="Chennai";
//		Demographics d0 = new Demographics( 600036, "Chennai");
		Demographics d1 = new Demographics("Chennai");
//		Demographics d2 = Demographics.create_object2( 600006, "Bhopal");
		
//		Demographics d2 = Demographics.create_object_util( 600006, "Bhopal");

		Demographics d3 = new Demographics( 45111, "Chennai");
		Demographics d4 = new Demographics( 451111, "Chennai");
		
		System.out.println(d4.city);  //same obj ref
//		System.out.println(d3);  //same obj ref
		System.out.println(d4==d3);   //true
		System.out.println(d1==d4);   //true
	}
}


class Demographics {
	
	
	String city;
	int pincode;
	
	
//	static HashMap<Integer, APL> object_pool = new HashMap<Integer, APL>();
	
	public Demographics() {

	}
	
	public Demographics(String city) {
		this.city = city;
		this.pincode = 451111;
		
	}
	
	public Demographics(int pincode, String city) {
	
		this.pincode = pincode;
		this.city = city;
		
	}
	
	
	
//	public boolean equals(Object o)
//	{
//		System.out.println("abc "+Interning4.a.incrementAndGet());
//		if (this == o)
//			return true;
//		return false;
//		
//	}
//	
//	public static void create_object_util( int pincode_, String city_) {
//		int pincode;
//		String city;
//		pincode =0;
//		city = null;
//		pincode = pincode_;
//		city = city_;
//		
////		return create_object2(pincode, city);
//	}
	
//	public static Demographics create_object2( int pincode_, String city_){
//		int hashvalue = Objects.hash(pincode_, city_);
//		if(object_pool.containsKey(hashvalue)) {
//			
//			APL firstAplNode = object_pool.get(hashvalue);
//			Demographics d = new Demographics( pincode_,city_);
//			Object obj = APL.search(firstAplNode, d, d.getClass().getName());
//			
//			if(obj==null) {
//				Demographics new_obj = new Demographics( pincode_,city_); 
//				APL newAplNode = new APL(new_obj);
//				APL.insert(firstAplNode, newAplNode);
//				object_pool.put(hashvalue, newAplNode);
//				return new_obj;
//			}
//			
//			Demographics demo = (Demographics)obj;
//			return demo;
//		}
//
//		Demographics new_obj = new Demographics( pincode_, city_);
//		APL newAplNode = new APL(new_obj);
//		object_pool.put(hashvalue, newAplNode);
//		return new_obj;
//	}
	
}
//now newAplNode is first
//if hashvalue not found-> create new obj
//obj == null  -> obj not found -> insert at front of linkedList
//	public static Demographics create_object0() {
		
		//copy lines from 0 arg constructor for field values
		//then call createObject2(this.city, this.pincode);
//		int hashvalue = Objects.hash(this.city, this.pincode);
//		if(map.containsKey(hashvalue)) {
//			Demographics obj = map.get(hashvalue);
//			if (obj == null || !(obj.city == city && obj.pincode == pincode)) {
//				Demographics new_obj = new Demographics();
//				map.put(hashvalue, new_obj);
//				return new_obj;
//			}
//			return obj;
//		}
//		Demographics new_obj = new Demographics();
//		map.put(hashvalue, new_obj);
//		return new_obj;
//	}
	
	
//	public boolean _equals(Object o) {
//		
//		if(this == o)
//			return true;
//	
//		if(o == null)
//			return false;
//		
//		if(getClass() != o.getClass())
//			return false;
//		
//		Demographics that = (Demographics)o;
//		return Objects.equals(city, that.city) && Objects.equals(pincode, that.pincode);
//	}


//
//private void <init>(java.lang.String, int)
//{
//    int @p1;
//    B$Methodology this;
//    java.lang.String @p0;
//
//    this := @this: B$Methodology;
//
//    @p0 := @parameter0: java.lang.String;
//
//    @p1 := @parameter1: int;
//
//    specialinvoke this.<java.lang.Enum: void <init>(java.lang.String,int)>(@p0, @p1);
//
//    return;
//}
//
//public void <init>(java.lang.String, int)
//{
//    int ordinal;
//    java.lang.String name;
//    B$Methodology this;
//
//    this := @this: B$Methodology;
//
//    name := @parameter0: java.lang.String;
//
//    ordinal := @parameter1: int;
//
//    specialinvoke this.<java.lang.Object: void <init>()>();
//
//    this.<java.lang.Enum: java.lang.String name> = name;
//
//    this.<java.lang.Enum: int ordinal> = ordinal;
//
//    return;
//}

//
//static void <clinit>()
//{
//    B$Methodology[] temp$2;
//    B$Methodology temp$0, temp$1, temp$3, temp$4;
//
//    temp$0 = new B$Methodology;
//
//    specialinvoke temp$0.<B$Methodology: void <init>(java.lang.String,int)>("ITERATE", 0);
//
//    <B$Methodology: B$Methodology ITERATE> = temp$0;
//
//    temp$1 = new B$Methodology;
//
//    specialinvoke temp$1.<B$Methodology: void <init>(java.lang.String,int)>("CONVERGE", 1);
//
//    <B$Methodology: B$Methodology CONVERGE> = temp$1;
//
//    temp$2 = newarray (B$Methodology)[2];
//
//    temp$3 = <B$Methodology: B$Methodology ITERATE>;
//
//    temp$2[0] = temp$3;
//
//    temp$4 = <B$Methodology: B$Methodology CONVERGE>;
//
//    temp$2[1] = temp$4;
//
//    <B$Methodology: B$Methodology[] $VALUES> = temp$2;
//
//    return;
//}
//static void <clinit>()
//{
//    B$Methodology[] temp$2;
//    java.util.HashMap object_pool;
//    B$Methodology temp$0, temp$1, temp$3, temp$4;
//
//    temp$0 = staticinvoke <B$Methodology: B$Methodology create_object_util2(java.lang.String,int)>("ITERATE", 0);
//
//    <B$Methodology: B$Methodology ITERATE> = temp$0;
//
//    temp$1 = staticinvoke <B$Methodology: B$Methodology create_object_util2(java.lang.String,int)>("CONVERGE", 1);
//
//    <B$Methodology: B$Methodology CONVERGE> = temp$1;
//
//    temp$2 = newarray (B$Methodology)[2];
//
//    temp$3 = <B$Methodology: B$Methodology ITERATE>;
//
//    temp$2[0] = temp$3;
//
//    temp$4 = <B$Methodology: B$Methodology CONVERGE>;
//
//    temp$2[1] = temp$4;
//
//    <B$Methodology: B$Methodology[] $VALUES> = temp$2;
//
//    object_pool = new java.util.HashMap;
//
//    specialinvoke object_pool.<java.util.HashMap: void <init>()>();
//
//    <B$Methodology: java.util.HashMap object_pool> = object_pool;
//
//    return;
//}
//
//
