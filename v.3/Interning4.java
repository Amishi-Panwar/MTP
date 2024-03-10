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
		Demographics d1 = new Demographics( 451111, "Chennai");
//		Demographics d2 = Demographics.create_object2( 600006, "Bhopal");
		
		Demographics d2 = Demographics.create_object_util( 600006, "Bhopal");

		Demographics d3 = new Demographics( 451111, "Chennai");
//		
		System.out.println(s);  //same obj ref
//		System.out.println(d3);  //same obj ref
		System.out.println(d1==d3);   //true
	}
}


class Demographics {
	String city;
	int pincode;
//	static HashMap<Integer, APL> object_pool;

	Demographics(int pincode_, String city_) {
	
		this.pincode = pincode_;
		this.city = city_;
	}
	
	Demographics(String city_) {
		this.city = city_;
		this.pincode = 451111;
	}
	
//	public boolean equals(Object o)
//	{
//		System.out.println("abc "+Interning4.a.incrementAndGet());
//		if (this == o)
//			return true;
//		return false;
//		
//	}
	
	public static Demographics create_object_util( int pincode_, String city_) {
		int pincode;
		String city;
		pincode = pincode_;
		city = city_;
		return create_object2(pincode, city);
	}
	
	public static Demographics create_object2( int pincode_, String city_){
		int hashvalue = Objects.hash(pincode_, city_);
		if(object_pool.containsKey(hashvalue)) {
			
			APL firstAplNode = object_pool.get(hashvalue);
			Demographics d = new Demographics( pincode_,city_);
			Object obj = APL.search(firstAplNode, d, d.getClass().getName());
			
			if(obj==null) {
				Demographics new_obj = new Demographics( pincode_,city_); 
				APL newAplNode = new APL(new_obj);
				APL.insert(firstAplNode, newAplNode);
				object_pool.put(hashvalue, newAplNode);
				return new_obj;
			}
			
			Demographics demo = (Demographics)obj;
			return demo;
		}

		Demographics new_obj = new Demographics( pincode_, city_);
		APL newAplNode = new APL(new_obj);
		object_pool.put(hashvalue, newAplNode);
		return new_obj;
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

}