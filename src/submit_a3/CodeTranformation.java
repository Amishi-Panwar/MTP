package submit_a3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.BooleanType;
import soot.IntType;
import soot.Local;
import soot.Modifier;
import soot.PackManager;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.jimple.AssignStmt;
import soot.jimple.EqExpr;
import soot.jimple.GotoStmt;
import soot.jimple.IfStmt;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.Stmt;
import soot.util.Chain;

public class CodeTranformation extends SceneTransformer{

	@Override
	protected void internalTransform(String phaseName, Map<String, String> options) {

		List <SootClass> allClasses = findAllClasses(); 
		List <SootMethod> allMethods = findAllMethods(allClasses);
		
		for(SootClass sootClass: allClasses) {
    		if(!isJDKClass(sootClass)) {
		    	for (SootMethod method : sootClass.getMethods()) {

			            Body body = method.retrieveActiveBody();
			            System.out.println(body);
		    	}
    		}
		}
			           
		addStaticFieldInClass("Demographics");
		overwriteEqualsMethod("Demographics");
		addMethodHashCode("Demographics");
//		addFactoryMethodCreateObject("Demographics");
		
//		
//		SootMethod m = Scene.v().getSootClass("A").getMethodByName("equals");
//		Body b = m.getActiveBody();
//		RefType refType = RefType.v("A");
//		SootClass sootClass = refType.getSootClass();
//		System.out.println(sootClass.getType());
	}
	
	public static void addStaticFieldInClass(String className) {
		
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
//	    targetClass.setApplicationClass();
        System.out.println(sootClass);
        RefType hashMapType = RefType.v("java.util.HashMap");
        RefType refTypeClass = RefType.v(sootClass);
        RefType refTypeInteger = RefType.v("java.lang.Integer");
        
        if (!sootClass.declaresFieldByName("object_pool")) {
        	
	        //generic HashMap
	        SootField staticHashMapField = new SootField("object_pool", hashMapType, Modifier.STATIC | Modifier.PUBLIC);
	        sootClass.addField(staticHashMapField);
	        
	        //<clinit> static initializer of class
	        SootMethod clinit = sootClass.getMethodByNameUnsafe("<clinit>");
	
		     // If <clinit> method doesn't exist yet, create it
		     if (clinit == null) {
		    	 
		         clinit = new SootMethod("<clinit>", Collections.emptyList(), VoidType.v(), Modifier.STATIC);
		         sootClass.addMethod(clinit);
		     }
	
		     JimpleBody body = Jimple.v().newBody(clinit);
		     clinit.setActiveBody(body);
		     PatchingChain<Unit> units = body.getUnits();
	
		     // Create a local for the HashMap
		     
		     //Shouldn't be in Local-- its a object field
		     Local object_pool = Jimple.v().newLocal("object_pool", hashMapType);
		     body.getLocals().add(object_pool);
	
		     // Create an assignment statement to initialize the HashMap
		     Stmt stmt = Jimple.v().newAssignStmt(object_pool, Jimple.v().newNewExpr(hashMapType));
		     units.add(stmt);
	

//		     stmt = Jimple.v().newInvokeStmt(Jimple.v().newSpecialInvokeExpr(object_pool, hashMapType.getSootClass().getMethodByName("<init>").makeRef(), Collections.emptyList()));
		     stmt = Jimple.v().newInvokeStmt(Jimple.v().newSpecialInvokeExpr(object_pool, Scene.v().getMethod("<java.util.HashMap: void <init>()>").makeRef(), Collections.<Value>emptyList()));
			    
		     units.add(stmt);
	
		     // Add the HashMap to the static field
		     stmt = Jimple.v().newAssignStmt(Jimple.v().newStaticFieldRef(staticHashMapField.makeRef()), object_pool);
		     units.add(stmt);
	
		     // Add the return void statement
		     stmt = Jimple.v().newReturnVoidStmt();
		     units.add(stmt);
        }
//        PackManager.v().writeOutput();

//        System.out.println("__________________NEW BODY____________________________________");
//        List <SootClass> allClasses = findAllClasses(); 
//		for(SootClass c: allClasses) {
//    		if(!isJDKClass(c)) {
//		    	for (SootMethod method : c.getMethods()) {
//
//			            Body body = method.retrieveActiveBody();
//			            System.out.println(body);
//		    	}
//    		}
//		}
	}
	
	
	public static void overwriteEqualsMethod(String className){
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
		
		SootMethod equalsMethod = null;
        for (SootMethod method : sootClass.getMethods()) {
            if (method.getName().equals("equals") && method.getParameterCount() == 1 &&
                    method.getParameterType(0).equals(RefType.v("java.lang.Object"))) {
                equalsMethod = method;
                break;
            }
        }
        
        //doubt parameters set--done
        if(equalsMethod == null) {
//        	List<> parameters = new ArrayList<>();
        	equalsMethod = new SootMethod("equals", new ArrayList<>(), BooleanType.v(), Modifier.PUBLIC);
            sootClass.addMethod(equalsMethod);
            
            Type objectType = RefType.v("java.lang.Object");
            // Create a list of parameter types
            ArrayList<Type> parameterTypes = new ArrayList<>();
            parameterTypes.add(objectType);

            // Set parameter types for the method
            equalsMethod.setParameterTypes(parameterTypes);
        } 

        	
            // Generate new body for equals method
            JimpleBody body = Jimple.v().newBody(equalsMethod);
            equalsMethod.setActiveBody(body);

            // Add code to equals method
            PatchingChain<Unit> units = body.getUnits();
            
            Local thisLocal = Jimple.v().newLocal("this", sootClass.getType());
            Local oLocal = Jimple.v().newLocal("o", RefType.v("java.lang.Object"));
            Local temp0 = Jimple.v().newLocal("temp$0", BooleanType.v());
            Local temp1 = Jimple.v().newLocal("temp$1", BooleanType.v());
            
            Stmt stmt = Jimple.v().newIdentityStmt(thisLocal, Jimple.v().newThisRef(sootClass.getType()));
            units.add(stmt);
            stmt = Jimple.v().newIdentityStmt(oLocal, Jimple.v().newParameterRef(RefType.v("java.lang.Object"), 0));
            units.add(stmt);
            
            Stmt targetStmt1 = Jimple.v().newAssignStmt(temp0, IntConstant.v(1));
            
            
            EqExpr referenceEqualityExpr = Jimple.v().newEqExpr(thisLocal, oLocal);
            //doubt in target and referenceCheck
            IfStmt ifStmt = Jimple.v().newIfStmt(referenceEqualityExpr, targetStmt1);
            units.add(ifStmt);

            //else condition label 2:
            Stmt targetStmt2 = Jimple.v().newAssignStmt(temp1, IntConstant.v(0));
            units.add(targetStmt2);
            stmt = Jimple.v().newRetStmt(temp1);
            units.add(stmt);
            
            //label 1: 
            units.add(targetStmt1);
            stmt = Jimple.v().newRetStmt(temp0);
            units.add(stmt);
//            PackManager.v().writeOutput();
        
      System.out.println("__________________NEW BODY____________________________________");
      List <SootClass> allClasses = findAllClasses(); 
		for(SootClass c: allClasses) {
  		if(!isJDKClass(c)) {
		    	for (SootMethod method : c.getMethods()) {

			            Body b = method.retrieveActiveBody();
			            System.out.println(b);
		    	}
  		}
		}
		
	}
	
	public static void addMethodHashCode(String className) {
		
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
		SootMethod zeroArgConstructor = null;
        for (SootMethod method : sootClass.getMethods()) {
            if (method.isConstructor() && method.getParameterCount() == 0) {
            	System.out.println("Method:" + method);
            	zeroArgConstructor = method;
                break;
            }
        }
        
//        Body body = zeroArgConstructor.retrieveActiveBody();
//        System.out.println(body);
        
//        if (zeroArgConstructor != null) {
//            // Create a new Jimple body for the constructor
//            JimpleBody body = Jimple.v().newBody(zeroArgConstructor);
//            zeroArgConstructor.setActiveBody(body);
//            PatchingChain<Unit> units = body.getUnits();
//
//            // Iterate over the units to find field assignment statements
////            for (Unit unit : units) {
////                if (unit instanceof AssignStmt) {
////                    AssignStmt assignStmt = (AssignStmt) unit;
////                    if (assignStmt.containsFieldRef()) {
////                        Value fieldValue = assignStmt.getRightOp();
////                        System.out.println("Field value: "+fieldValue);
////                        // Generate the Objects.hash() call
//////                        InvokeExpr hashCall = Jimple.v().newStaticInvokeExpr(
//////                                Scene.v().makeMethodRef(
//////                                        Scene.v().getSootClass("java.util.Objects"),
//////                                        "hash",
//////                                        java.util.Collections.singletonList(IntType.v()), // Result type is int
//////                                        java.util.Collections.singletonList(fieldValue.getType()) // Argument type
//////                                ),
//////                                java.util.Collections.singletonList(fieldValue)
//////                        );
//////                        units.insertBefore(Jimple.v().newInvokeStmt(hashCall), assignStmt);
////                    }
////                }
////            }
//        }
        
//		Chain<SootField> allFeilds = sootClass.getFields();
//		for(SootField f: allFeilds)
//			System.out.println("field: "+f);
//		SootMethod hashCodeMethod = new SootMethod("hashCode", new ArrayList<>(), IntType.v(), Modifier.PRIVATE);
//        sootClass.addMethod(hashCodeMethod);
        
        
	}
	
	public static void addZeroArgumentConstructor(SootMethod constructor) {
		
	}
	
	public static void addMultipleArgumentConstructor(SootMethod constructor) {
		
	}
	
	
	public static void addFactoryMethodCreateObjectUtil(SootMethod constructor) {
		
		if(constructor.getParameterCount()==0) {
			addZeroArgumentConstructor(constructor);
		}else {
			addMultipleArgumentConstructor(constructor);
		}
		
	}
	public static void addFactoryMethodCreateObject(String className) {
		
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
		SootMethod zeroArgConstructor = null;
        for (SootMethod method : sootClass.getMethods()) {
            if (method.isConstructor()) {
            	addFactoryMethodCreateObjectUtil(method);
            	System.out.println("Method:" + method);
            	
            }
        }
        
		
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
		
		RefType refTypeClass = RefType.v(className);
		//create empty method
		//doubt in parameter Type
//		SootMethod(String name, List parameterTypes, Type returnType, int modifiers)
		SootMethod create_object_method = new SootMethod("create_object", new ArrayList<>(), refTypeClass, Modifier.PRIVATE | Modifier.STATIC);
		
		//add method to class
		sootClass.addMethod(create_object_method);
		
		//create body of method 
		JimpleBody body = Jimple.v().newBody(create_object_method);
		create_object_method.setActiveBody(body);
		
		
		//create new local variable and add to set of locals
		Local hashValue_local = Jimple.v().newLocal("hashValue", IntType.v());
		body.getLocals().add(hashValue_local);
		
		//
		SootMethod toCall = Scene.v().getMethod("<java.io.PrintStream: void println(java.lang.String)>");
//		SootMethod toCall = Scene.v().getMethod("<java.util.Objects: int hash()>")
		
		Chain units = body.getUnits();
		
		RefType refTypeObject = RefType.v("Object");
		
//		java.util.List args
//		 Value... args
//	    units.add(Jimple.v().newInvokeStmt
//	        (Jimple.v().newVirtualInvokeExpr
//	           (refTypeObject, toCall.makeRef(), Arrays.asList(args))));
		
	}
	
	
	 public static List<SootClass>findAllClasses(){
	    	Chain <SootClass> classes = Scene.v().getApplicationClasses(); // Get the Chain of classes		
		    List <SootClass> listClasses = new ArrayList <>(classes); // Convert Chain to ArrayList
		    List<SootClass> nonLibraryClasses = new ArrayList<>();
		    for(SootClass c: listClasses) {
		    	if(!c.isLibraryClass() && !isJDKClass(c)) {
		    		nonLibraryClasses.add(c);
		    	}
		    }
		    return nonLibraryClasses;
	  }
	    
     
	 public static boolean isJDKClass(SootClass sootClass) {
	     String packageName = sootClass.getPackageName();
	     return packageName.startsWith("java.") || packageName.startsWith("javax.") || sootClass.toString().startsWith("jdk");
	 }

	 public List<SootMethod> findAllMethods(List<SootClass>classes) {
			
        List<SootMethod>methods = new ArrayList<>();

        for(SootClass c: classes) {
        	if(!isJDKClass(c)) {
	             for(SootMethod method: c.getMethods()) {
	                 methods.add(method);
	             }
        	}
        }
        return methods;

    }

}

	

