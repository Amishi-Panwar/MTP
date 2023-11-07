import java.io.*;
import java.util.*;

public class Interning3 {
	public static void main(String [] args)
	{
		Demographics d1 = new Demographics("Chennai", 600036);
		d1.create_object("Chennai", 600036);

	}
}

class Demographics {
	 String city;
	 int pincode;
	static HashMap<Integer, Demographics> object_pool = new HashMap<Integer, Demographics>();


	Demographics() {
		this.city = "Bhopal";
		this.pincode = 451111;
	}
	 
	Demographics(String city_, int pincode_) {
		this.city = city_;
		this.pincode = pincode_;
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
	
	public static void create_object(String city_, int pincode_){
		
		int hashvalue = Objects.hash(city_, pincode_);
//		
//		//if hashvalue found
		if(object_pool.containsKey(hashvalue)) {
			Demographics obj = object_pool.get(hashvalue);
//			
//			//if hashvalue found but obj is null or obj fields not matching-> create new obj and put in map
			if (obj == null || !(obj.city == city_ && obj.pincode == pincode_)) {
				Demographics new_obj = new Demographics(city_, pincode_);
//				object_pool.put(hashvalue, new_obj);
//				return new_obj;
			}
//			
//			//else return obj from map
//			return obj;
		}
//		
//		//if hashvalue not found-> create new obj
		Demographics new_obj = new Demographics(city_, pincode_);
		object_pool.put(hashvalue, new_obj);
//		return new_obj;
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



