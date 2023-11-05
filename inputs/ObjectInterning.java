import java.io.*;
import java.util.*;

public class ObjectInterning {

	public static void main(String[] args) {
		String s1= "A";
		String s2 = s1.concat("B");
		String temp ="AB";
		String s3 = s2.intern();
		
		System.out.println(s2 == s3);

	}
}
