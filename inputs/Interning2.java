
import java.io.*;
import java.util.*;

public class Interning2 {
	public static void main(String [] args)
	{
//		Demographics d1=Demographics.create_object();
//		Demographics d2=Demographics.create_object();
//		Userdetails u1= new Userdetails(1,"A",d1);
//		Userdetails u2=new Userdetails(2,"B",d1);
//		Userdetails u3=new Userdetails(3,"C",d2);
//		Userdetails u4=new Userdetails(4,"D",d2);
//		System.out.println(u1+"\n"+u2+"\n"+u3+"\n"+u4);
//		if(u1.d == u2.d)
//			System.out.println("true");
	}
}

class Demographics {
	 String city;
	 int pincode;
//	static HashMap<Integer, Demographics> map = new HashMap<Integer, Demographics>();


	 Demographics() {
		this.city = "Bhopal";
		this.pincode = 451111;
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

	@Override
	public boolean equals(Object o)
	{
		// We have a guarantee that if Objects have the same
		//references then they are same and if Objects have different
		//references then they are different.
		if (this == o)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return ",City : " + this.city + " ,Pincode : " + this.pincode;
	}

	//remove hashCode - Exception- static reference to a unstatic method
	@Override
	public int hashCode() {
		return Objects.hash(this.city, this.pincode);
	}
}

class Userdetails {
	String name;
	int userid;
//	Demographics d;

	Userdetails( int userid,String name)
	{
		this.name=name;
		this.userid=userid;
//		this.d=d;
	}
	
//	@Override
//	public String toString()
//	{
//		return "Userid : "+userid+" ,Name : "+name+" "+d.toString();
//	}

}


