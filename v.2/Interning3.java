import java.io.*;
import java.util.*;

public class Interning3 {
	public static void main(String [] args)
	{
		Demographics d1 = new Demographics( 600036, "Chennai");
		d1.create_object( 600036, "Chennai");
		Object o = new Object();
		APL a = new APL(o);
	}
}

class Demographics {
	 String city;
	 int pincode;
	static HashMap<Integer, Demographics> object_pool = new HashMap<Integer, Demographics>();
//	static HashMap<Integer, APL> object_pool1 = new HashMap<Integer, APL>();

	Demographics() {
		this.city = "Bhopal";
		this.pincode = 451111;
	}
	 
	Demographics(int a, String b) {
		this.pincode = a;
		this.city = b;
		
	}

//	public static Demographics create_object() {
//		int hashvalue = Objects.hash(city, pincode);
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
	
	public static Demographics create_object(int a, String b){
		
		int hashvalue = Objects.hash(a, b);
//		
//		//if hashvalue found
		if(object_pool.containsKey(hashvalue)) {
			Demographics obj = object_pool.get(hashvalue);
//			
//			//obj fields not matching-> create new obj and put in map
			if (!(obj.city == b && obj.pincode == a)) {
				Demographics new_obj = new Demographics(a, b);
				object_pool.put(hashvalue, new_obj);
				return new_obj;
			}
//			
//			//else return obj from map
			return obj;
		}
		
//		//if hashvalue not found-> create new obj
		Demographics new_obj = new Demographics(a, b);
		object_pool.put(hashvalue, new_obj);
		return new_obj;
	}

//	@Override
//	public boolean equals(Object o)
//	{
//
//		if (this == o)
//			return true;
//		return false;
//	}

//	@Override
//	public String toString() {
//		return ",City : " + this.city + " ,Pincode : " + this.pincode;
//	}

	//remove hashCode - Exception- static reference to a unstatic method
//	@Override
//	public int hashCode() {
//		return Objects.hash(this.city, this.pincode);
//	}
}



