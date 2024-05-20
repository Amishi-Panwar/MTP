import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.util.*;

public class APL {
	Object node;
	APL next;
	
	APL(Object node_){
		node = node_;
		next = null;
	}
	
	public static Object search(APL apl, Object thatObj, String className) {

		APL curr = apl;
		while(curr != null) {
			
			Object thisObj = curr.node;
				try {
		            Class<?> clazz = Class.forName(className);
		            System.out.println("clazz- " + clazz);
		            
		            if (clazz.isInstance(thisObj)) {

		                boolean areFieldsEqual = compareFields(clazz, thisObj, thatObj);
		                
		                
		                if(areFieldsEqual==true) {
		                	System.out.println("Object present in LinkedList " );
		                	return thisObj;
		                }
		                
		            } else {
		                System.out.println("Cannot cast to " + className);
		            }
				} catch (ClassNotFoundException e) {
		            System.out.println("Class not found: " + className);
				}
			curr = curr.next;
	    }
		return null;
	}
	
	public static boolean compareFields(Class<?> clazz, Object obj1, Object obj2) {
		
		Field[] fields = getAllFields(clazz);
		
		for (Field field : fields) {
			
			try {
	            field.setAccessible(true);
	            
	            Object fieldValue1 = field.get(obj1);
	            Object fieldValue2 = field.get(obj2);
	
	            System.out.println("fieldValue1- "+ field+ " == "+fieldValue1);
	            System.out.println("fieldValue2- "+ fieldValue2);
	            
	            if (!Objects.equals(fieldValue1, fieldValue2)) {
	            	
	            	System.out.println("Field is array/list/Object");
	                // Field values are not equal
	                if (field.getType().isArray()) {
	                	
	                	Object[] array1 = convertToArray(fieldValue1);
	                    Object[] array2 = convertToArray(fieldValue2);
	                    
	                    if(!Arrays.deepEquals(array1, array2)) {
	                    	System.out.println("Arrays values not equal");
	                        return false; // Arrays are not equal
	                    }
	                    	
	                } else if (fieldValue1 instanceof List && fieldValue2 instanceof List) {
	                	
	                    // Handle List comparison
	                    if (!listEquals((List<?>) fieldValue1, (List<?>) fieldValue2)) {
	                        return false; // Lists are not equal
	                    }
	                    
	                } else
	                	return false;
	                
//	                else {
//	                	
//    	 	        	System.err.println("Unhandled FieldType in APL- " +field);
//    	 	        	assert (false);
//    	 	        	System.exit(1);
//    	 	        
//	                }
	            }
	            
	        } catch (IllegalArgumentException e) {
				System.out.println("APL Exception");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.out.println("APL Exception");
				e.printStackTrace();
			}
			catch(Exception e) {
				System.out.println("APL Exception");
				System.out.println("Exception General");
				e.printStackTrace();
			}
        }

        return true; // All fields are equal
	}
	
	public static void insert(APL firstNode, APL newNode) {
		
		newNode.next = firstNode;
		
	}

	
	
	public static Field[] getAllFields(Class<?> clazz) {
		
		List<Field> fields = new ArrayList<>();

        // Add fields of the current class- public+private+protected
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        // Recursively add fields of superclasses
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            Field[] superFields = getAllFields(superClass);
            fields.addAll(Arrays.asList(superFields));
        }

        // Convert list to array and return
        return fields.toArray(new Field[0]);
	}
	
	public static Object[] convertToArray(Object obj) {
        if (obj == null) {
            return null;
        }

        Class<?> componentType = obj.getClass().getComponentType();
        System.out.println("componentType- "+ componentType);
        if (componentType == null) {
            throw new IllegalArgumentException("Object is not an array.");
        }

        int length = Array.getLength(obj);
        Object array[] = new Object[length];
//        Object[] array = (Object[]) Array.newInstance(componentType, length);
        System.out.println("array- "+ array);
        
        for (int i = 0; i < length; i++) {
        	System.out.println("Array.get(obj, i)-- "+ Array.get(obj, i));
            array[i] = Array.get(obj, i);
        }

        return array;
    }

	

    private static boolean listEquals(List<?> list1, List<?> list2) {
        if (list1 == null || list2 == null) {
            return list1 == list2;
        }
        return list1.equals(list2);
    }
	
	
}

//No need to cast
//Object castedObject = clazz.cast(thisObj);
//Field[] thisObjFields = castedObject.getClass().getDeclaredFields();

//protected void <init>(avrora.sim.platform.ExternalFlash, int)
//{
//    int numBytes;
//    avrora.sim.platform.ExternalFlash$Page this;
//    short[] $stack3;
//    avrora.sim.platform.ExternalFlash l1;
//
//    this := @this: avrora.sim.platform.ExternalFlash$Page;
//
//    l1 := @parameter0: avrora.sim.platform.ExternalFlash;
//
//    numBytes := @parameter1: int;
//
//    this.<avrora.sim.platform.ExternalFlash$Page: avrora.sim.platform.ExternalFlash this$0> = l1;
//
//    specialinvoke this.<java.lang.Object: void <init>()>();
//
//    $stack3 = newarray (short)[numBytes];
//
//    this.<avrora.sim.platform.ExternalFlash$Page: short[] bytes> = $stack3;
//
//    return;
//}
