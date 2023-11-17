package submit_a3;


//TODO --> 

//make zero arg createObject method
//DYNAMICALLY CHECK int to Integer conversion , double to Double if required
//don't throw away equals
//temp variable naming 
//make all constructors private
//DONE-returns stmt in createObject

//DISCUSSIONS-->
//Mapping fields of class to what provided in constructor--> redundant or not

//naming createObject1, createObject2(_,_) based on no. of args and calling them correspondingly


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import soot.ArrayType;
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
import soot.SootMethodRef;
import soot.Type;
import soot.Unit;
import soot.Value;
import soot.VoidType;
import soot.jimple.AssignStmt;
import soot.jimple.EqExpr;
import soot.jimple.FieldRef;
import soot.jimple.GotoStmt;
import soot.jimple.IfStmt;
import soot.jimple.IntConstant;
import soot.jimple.InvokeExpr;
import soot.jimple.Jimple;
import soot.jimple.JimpleBody;
import soot.jimple.NullConstant;
import soot.jimple.Stmt;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.JNewExpr;
import soot.util.Chain;

public class CodeTranformation extends SceneTransformer{

	@Override
	protected synchronized void internalTransform(String phaseName, Map<String, String> options) {

		List <SootClass> allClasses = findAllClasses(); 
		List <SootMethod> allMethods = findAllMethods(allClasses);
		
		for(SootClass sootClass: allClasses) {
			if(sootClass.getName().equals("Demographics")) {
    		if(!isJDKClass(sootClass)) {
		    	for (SootMethod method : sootClass.getMethods()) {

			            Body body = method.retrieveActiveBody();
			            System.out.println(body);
		    	}
    		}
			}
		}
			           
		addStaticFieldInClass("Demographics");
		overwriteEqualsMethod("Demographics");
//		addMethodHashCode("Demographics");
		
		addFactoryMethodCreateObject("Demographics");
		
//		for(SootClass sc: allClasses) {
//			for(SootMethod sm: sc.getMethods()) {
//				if(sm.hasActiveBody()) {
//					Body body = sm.getActiveBody();
//					PatchingChain<Unit> units = body.getUnits();
//					Iterator<Unit> iter = units.iterator();
//                    while (iter.hasNext()) {
//                        Unit u = iter.next();
//                        if (u instanceof JAssignStmt) {
//                        	JAssignStmt assignStmt = (JAssignStmt) u;
//                        	System.out.println("assignStmt: "+assignStmt);
//                        	Value rightOp = assignStmt.getRightOp();
//                        	if(rightOp instanceof JNewExpr) {
//                        		RefType baseType = ((JNewExpr)rightOp).getBaseType();
//                        		if(baseType.toString().equals(sc.getName())) {
////                        			((JNewExpr)rightOp
//                        		}
//                        	}
//                        		
////                        		String methodName = "<";
////                        		methodName += sc;
////                        		methodName += ":";
////                        		methodName += " "+sc;
////                        		methodName += "createObject";
////                        		methodName += ;
//                        		
////                        		Stmt createObjectStmt = Jimple.v().newInvokeStmt(Jimple.v().newStaticInvokeExpr(
////                                        Scene.v().getMethod("<YourClass: Demographics createObject(java.lang.String,int)>").makeRef(),
////                                        Arrays.asList(
////                                            StringConstant.v("parameter1"), // Example parameter
////                                            IntConstant.v(42) // Example parameter
////                                        )
////                                    )
////                                );
////                        	}
//                        }
//                    }
//				}
//			}
//		}
		
//		SootMethod m = Scene.v().getSootClass("A").getMethodByName("equals");
//		Body b = m.getActiveBody();
//		RefType refType = RefType.v("A");
//		SootClass sootClass = refType.getSootClass();
//		System.out.println(sootClass.getType());
	}
	
	public static synchronized void addStaticFieldInClass(String className) {
		
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
	
	
	public static synchronized void overwriteEqualsMethod(String className){
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
            
            body.getLocals().add(thisLocal);
            body.getLocals().add(oLocal);
            body.getLocals().add(temp0);
            body.getLocals().add(temp1);
            
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
            stmt = Jimple.v().newReturnStmt(temp1);
//            stmt = Jimple.v().newRetStmt(temp1);
            units.add(stmt);
            
            //label 1: 
            units.add(targetStmt1);
            stmt = Jimple.v().newReturnStmt(temp0);
//            stmt = Jimple.v().newRetStmt(temp0);
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
	

	public static synchronized void addZeroArgumentConstructor(SootMethod constructor) {
		
	}
	public static void mapParametersToFields(SootMethod constructor, String className, HashMap<SootField, String> parameterMapping) {
		Body body = constructor.retrieveActiveBody();
        System.out.println(body);
        for (Unit unit : body.getUnits()) {
            if (unit instanceof Stmt) {
                Stmt stmt = (Stmt) unit;
                // Check if the statement is an assignment to a field
                if (stmt.containsFieldRef()) {
                    // This statement involves a field assignment in the constructor
                    System.out.println("Field Assignment in Constructor: " + stmt);
                    
                    FieldRef fieldRef = stmt.getFieldRef();
                    SootField field = fieldRef.getField();

                    Value rhsValue = null;
                    if (stmt instanceof AssignStmt) {
                        rhsValue = ((AssignStmt) stmt).getRightOp();
                    }
                    System.out.println("Field Assignment in Constructor: " + field + " "+rhsValue.toString());
                    parameterMapping.put(field, rhsValue.toString());
                }
            }
        }
	}
	
	public static synchronized void addMultipleArgumentConstructor(SootMethod constructor, String className) {
		
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
		
        
        List<Type> parameterTypesList = constructor.getParameterTypes();
        for(Type type: parameterTypesList) {
        	System.out.println("type: "+type);
        }
        
        /* DEFINING METHOD*/
        
        //public static void create_object(String city_, int pincode_)
        String createObjectMethodName = "createObject"+ parameterTypesList.size();
        SootMethod createObjectMethod = new SootMethod(createObjectMethodName, new ArrayList<>(), Scene.v().getSootClass(className).getType(), Modifier.PUBLIC | Modifier.STATIC);

        sootClass.addMethod(createObjectMethod);

        //Set parameter types for the method
        createObjectMethod.setParameterTypes(parameterTypesList);
        /* END- DEFINING METHOD*/
        
        JimpleBody body = Jimple.v().newBody(createObjectMethod);
        createObjectMethod.setActiveBody(body);
        Body bodyConstructor = constructor.retrieveActiveBody();

        PatchingChain<Unit> units = body.getUnits();

        /* ADDING LOCALS FOR PARAMETERS*/
        HashMap<SootField, String> parameterMapping = new HashMap<SootField, String>();
        mapParametersToFields(constructor, className, parameterMapping);
        List<String> parameterName = new ArrayList<>();
        List<Local> parameterList = bodyConstructor.getParameterLocals();
        for(int i=0; i<parameterList.size(); i++) {
        	 Local local = parameterList.get(i);
        	 Local parmeterLocal = Jimple.v().newLocal(local.getName(), local.getType());
        	 body.getLocals().add(parmeterLocal);
	   		 Stmt stmt = Jimple.v().newIdentityStmt(parmeterLocal, Jimple.v().newParameterRef(local.getType(), i));
	   		 units.add(stmt);	
        	 parameterName.add(local.getName());
        	 System.out.println("local: "+local);
        }
        /* END - ADDING LOCALS FOR PARAMETERS*/
        
        /* DEFINING OBJECT ARRAY for giving parameter to Objects.hash()*/
        Local temp$0 = Jimple.v().newLocal("temp$0", ArrayType.v(RefType.v("java.lang.Object"), 1));
        body.getLocals().add(temp$0);
        
        Stmt newArrayStmt = Jimple.v().newAssignStmt(temp$0,Jimple.v().newNewArrayExpr(RefType.v("java.lang.Object"), IntConstant.v(parameterList.size())));
        units.add(newArrayStmt);
        
        /*END- DEFINING OBJECT ARRAY for giving parameter to Objects.hash()*/
        
        //TO REMOVE
//        int k=1;
//        for(int i=0;i<parameterList.size();i++) {
//        	Local local = parameterList.get(i);
//        	Value value ;
//        	if(local.getType() instanceof IntType) {
//        		
//        		Local integerValue = Jimple.v().newLocal("temp$"+k++, RefType.v("java.lang.Integer"));
//        		SootClass _IntegerClass = Scene.v().getSootClass("java.lang.Integer");
//                SootMethodRef _IntegerValueOf = Scene.v().makeMethodRef(_IntegerClass, "valueOf", Arrays.asList(IntType.v()), RefType.v("java.util.Integer"), true);
//                List<Local> list = new ArrayList<>();
//                list.add(local);
//                Value _Integerrvalue = Jimple.v().newStaticInvokeExpr(_IntegerValueOf, list);
//                Stmt stmt = Jimple.v().newAssignStmt(integerValue, _Integerrvalue);
//                units.add(stmt);
//                value = integerValue;
//        	}else {
//        		value = local;
//        	}
//        	
//        	Stmt assignmentValueStmt = Jimple.v().newAssignStmt(Jimple.v().newArrayRef(temp$0, IntConstant.v(i)), value);
//        	units.add(assignmentValueStmt);
//        }
      //TO REMOVE
        
        //int hashvalue = Objects.hash(city_, pincode_);
        
        Local temp$2 = Jimple.v().newLocal("temp$2", IntType.v());
        body.getLocals().add(temp$2);
        Local hashvalue = Jimple.v().newLocal("hashvalue", IntType.v());
        body.getLocals().add(hashvalue);
        
        SootClass _ObjectsClass = Scene.v().getSootClass("java.util.Objects");
        SootMethodRef _ObjectsHashMethodRef = Scene.v().makeMethodRef(_ObjectsClass, "hash", Arrays.asList(temp$0.getType()), IntType.v(), true);
        Value rvalueHashvalue = Jimple.v().newStaticInvokeExpr(_ObjectsHashMethodRef, temp$0);
        Stmt hashvalueStmt = Jimple.v().newAssignStmt(temp$2, rvalueHashvalue);
        units.add(hashvalueStmt);
        Stmt assignmentStmt = Jimple.v().newAssignStmt(hashvalue, temp$2);
        units.add(assignmentStmt);
        
        //previous
//        SootClass _ObjectsClass = Scene.v().getSootClass("java.util.Objects");
//        SootMethodRef _ObjectsHashMethodRef = Scene.v().makeMethodRef(_ObjectsClass, "hash", parameterTypesList, IntType.v(), true);
//        Value rvalueHashvalue = Jimple.v().newStaticInvokeExpr(_ObjectsHashMethodRef, parameterList);
//        Stmt hashvalueStmt = Jimple.v().newAssignStmt(hashvalue, rvalueHashvalue);
//        units.add(hashvalueStmt);
        
        
        /*IF condition(contains.key()) EXPR*/
        
        SootField objectPoolField = Scene.v().getSootClass(className).getFieldByName("object_pool");
        Local temp$3 = Jimple.v().newLocal("temp$3", objectPoolField.getType());
        body.getLocals().add(temp$3);
        
        Stmt stmt = Jimple.v().newAssignStmt(temp$3, Jimple.v().newStaticFieldRef(objectPoolField.makeRef()));
        units.add(stmt);
        
        Local temp$4 = Jimple.v().newLocal("temp$4", Scene.v().getSootClass("java.lang.Integer").getType());
        body.getLocals().add(temp$4);
        
       
        SootClass _IntegerClass = Scene.v().getSootClass("java.lang.Integer");
        SootMethodRef _IntegerValueOf = Scene.v().makeMethodRef(_IntegerClass, "valueOf", Arrays.asList(IntType.v()), RefType.v("java.util.Integer"), true);
        List<Local> list = new ArrayList<>();
        list.add(hashvalue);
        Value _Integerrvalue = Jimple.v().newStaticInvokeExpr(_IntegerValueOf, list);
        stmt = Jimple.v().newAssignStmt(temp$4, _Integerrvalue);
        units.add(stmt);
        
		Local temp$5 = Jimple.v().newLocal("temp$5", Scene.v().getSootClass(className).getType());
        SootMethodRef _MapContainsKey = Scene.v().makeMethodRef(Scene.v().getSootClass("java.util.HashMap"),"containsKey",Arrays.asList(RefType.v("java.lang.Object")), BooleanType.v(), false);
        Value rvalueMapContainsKey = Jimple.v().newVirtualInvokeExpr(temp$3, _MapContainsKey, temp$4);
        Stmt mapContainsKeyStmt = Jimple.v().newAssignStmt(temp$5, rvalueMapContainsKey);
        units.add(mapContainsKeyStmt);
        
        /*END - IF condition(contains.key()) EXPR*/
        
        
        /*target stmt --> remaining stmts are in Label 1 - this is also a stmt of label 1*/
        
        Local temp$10 = Jimple.v().newLocal("temp$10",Scene.v().getSootClass(className).getType());
        body.getLocals().add(temp$10);
        Stmt newObjStmt = Jimple.v().newAssignStmt(temp$10,Jimple.v().newNewExpr(Scene.v().getSootClass(className).getType()));
        /*END- target stmts*/
        
        
        /*ACTUAL IF STMT*/
        EqExpr eqExpr = Jimple.v().newEqExpr(temp$5, IntConstant.v(0));

        IfStmt ifStmt = Jimple.v().newIfStmt(eqExpr, newObjStmt);
        units.add(ifStmt);
        /*END- ACTUAL IF STMT*/
        
        /*INSIDE IF BLOCK*/
      
        //Demographics obj = object_pool.get(hashvalue);
        Local obj = Jimple.v().newLocal("obj", Scene.v().getSootClass(className).getType());
        
        objectPoolField = Scene.v().getSootClass(className).getFieldByName("object_pool");
        Local temp$6 = Jimple.v().newLocal("temp$6", objectPoolField.getType());
        body.getLocals().add(temp$6);
        
        //Target Stmt
        //Label 2
        Stmt getMapStmt = Jimple.v().newAssignStmt(temp$6, Jimple.v().newStaticFieldRef(objectPoolField.makeRef()));
        units.add(getMapStmt);
        
        Local temp$7 = Jimple.v().newLocal("temp$7", Scene.v().getSootClass("java.lang.Integer").getType());
        body.getLocals().add(temp$7);
        
        _IntegerClass = Scene.v().getSootClass("java.lang.Integer");
        _IntegerValueOf = Scene.v().makeMethodRef(_IntegerClass, "valueOf", Arrays.asList(IntType.v()), RefType.v("java.util.Integer"), true);
        list = new ArrayList<>();
        list.add(hashvalue);
        _Integerrvalue = Jimple.v().newStaticInvokeExpr(_IntegerValueOf, list);
        stmt = Jimple.v().newAssignStmt(temp$7, _Integerrvalue);
        units.add(stmt);
        
//        Value rvalueMapGet = Jimple.v().newVirtualInvokeExpr(temp$6, Scene.v().makeMethodRef(Scene.v().getSootClass("java.util.HashMap"), "get", Arrays.asList(RefType.v("java.lang.Object")), RefType.v("java.lang.Object"), false));
        Local temp$8 = Jimple.v().newLocal("temp$8", Scene.v().getSootClass(className).getType());
        SootMethodRef _MapGet = Scene.v().makeMethodRef(Scene.v().getSootClass("java.util.HashMap"),"get",Arrays.asList(IntType.v()), RefType.v(className), false);
        Value rvalueMapGet = Jimple.v().newVirtualInvokeExpr(temp$6, _MapGet, temp$7);
        Stmt mapGetStmt = Jimple.v().newAssignStmt(temp$8, rvalueMapGet);
        units.add(mapGetStmt);
        
        Local temp$9 = Jimple.v().newLocal("temp$9", Scene.v().getSootClass(className).getType());
        Stmt castStmt = Jimple.v().newAssignStmt(temp$9, Jimple.v().newCastExpr(temp$8, Scene.v().getSootClass(className).getType()));
        units.add(castStmt);
        stmt  = Jimple.v().newAssignStmt(obj, temp$9);
        units.add(stmt);
        Stmt returnStmt = Jimple.v().newReturnStmt(obj);
        units.add(returnStmt);
        /* END- INSIDE IF BLOCK*/
        
        //2nd if condition targetStmt
        	//TO BE DISCUSSED
//        Local temp$20 = Jimple.v().newLocal("temp$20",Scene.v().getSootClass(className).getType());
//        body.getLocals().add(temp$20);
//        Stmt tamp$20newObjStmt = Jimple.v().newAssignStmt(temp$20,Jimple.v().newNewExpr(Scene.v().getSootClass(className).getType()));
//        
//        //2nd if condition
//        Chain<SootField> classFields = sootClass.getFields();
//        Value fieldValues[] = new Value[classFields.size()];
//        for(int i=0; i<classFields.size(); i++) {
//        	fieldValues[i] = Jimple.v().newInstanceFieldRef(obj, Scene.v().getField(classFields.toString()).makeRef());
//        }
        
//        Value cityComparison = Jimple.v().newEqExpr(cityFieldValue, city_);
//        Value pincodeComparison = Jimple.v().newEqExpr(pincodeFieldValue, pincode_);
//        Value combinedComparison = Jimple.v().newAndExpr(cityComparison, pincodeComparison);
//        Value param1FieldValue = Jimple.v().newInstanceFieldRef(obj, Scene.v().getField(createObjectMethodName));
//        Stmt conditionStmt = Jimple.v().newIfStmt(Jimple.v().newOrExpr( Jimple.v().newEqExpr(obj, NullConstant.v()),Jimple.v().newNeExpr(
//        	            Jimple.v().newEqExpr(obj.getFieldByName("city"), city_),
//        	            Jimple.v().newEqExpr(obj.getFieldByName("pincode"), pincode_)
//        	        )
//        	    ),
//        	    targetLabel // Specify the target label for the true branch
//        	);
        
        //END OF Label 2
        //END - TO BE DISCUSSED
        
        /* OUTSIDE IF BLOCK */
        //target stmt of if
        //Label 1
        units.add(newObjStmt);
        Local new_obj = Jimple.v().newLocal("new_obj",Scene.v().getSootClass(className).getType());

        body.getLocals().add(new_obj);

        SootMethodRef initMethodRef = Scene.v().makeConstructorRef(Scene.v().getSootClass(className), parameterTypesList);
        Stmt invokeInitStmt = Jimple.v().newInvokeStmt(Jimple.v().newSpecialInvokeExpr(temp$10, initMethodRef, parameterList));
        units.add(invokeInitStmt);
        
        assignmentStmt = Jimple.v().newAssignStmt(new_obj, temp$10);
        units.add(assignmentStmt);
        
        //put in map
        objectPoolField = Scene.v().getSootClass(className).getFieldByName("object_pool");
        Local temp$11 = Jimple.v().newLocal("temp$11", objectPoolField.getType());
        body.getLocals().add(temp$11);
        
        stmt = Jimple.v().newAssignStmt(temp$11, Jimple.v().newStaticFieldRef(objectPoolField.makeRef()));
        units.add(stmt);
        
        Local temp$12 = Jimple.v().newLocal("temp$12", Scene.v().getSootClass("java.lang.Integer").getType());
        body.getLocals().add(temp$12);
        
        _IntegerClass = Scene.v().getSootClass("java.lang.Integer");
        _IntegerValueOf = Scene.v().makeMethodRef(_IntegerClass, "valueOf", Arrays.asList(IntType.v()), RefType.v("java.util.Integer"), true);
        list = new ArrayList<>();
        list.add(hashvalue);
        _Integerrvalue = Jimple.v().newStaticInvokeExpr(_IntegerValueOf, list);
        stmt = Jimple.v().newAssignStmt(temp$12, _Integerrvalue);
        units.add(stmt);
        
		Local temp$13 = Jimple.v().newLocal("temp$13", Scene.v().getSootClass(className).getType());
        SootMethodRef _MapPut = Scene.v().makeMethodRef(Scene.v().getSootClass("java.util.HashMap"),"put",Arrays.asList(RefType.v("java.lang.Object"), RefType.v("java.lang.Object")), RefType.v("java.lang.Object"), false);
        List<Value> values = new ArrayList<>();
        values.add(temp$12);
        values.add(new_obj);
        Value rvalueMapPut = Jimple.v().newVirtualInvokeExpr(temp$11, _MapPut, values);
   
        Stmt mapPutStmt = Jimple.v().newAssignStmt(temp$13, rvalueMapPut);
        units.add(mapPutStmt);
        
        Local temp$14 = Jimple.v().newLocal("temp$14", Scene.v().getSootClass(className).getType());
        castStmt = Jimple.v().newAssignStmt(temp$14, Jimple.v().newCastExpr(temp$13, Scene.v().getSootClass(className).getType()));
        units.add(castStmt);
        returnStmt  = Jimple.v().newReturnStmt(new_obj);
        units.add(returnStmt);
        /* END OUTSIDE IF BLOCK */
//        units.add(returnStmt);
        System.out.println("BODY: ");
        System.out.println(body);
        
        
        
//		Local thisLocal = Jimple.v().newLocal("this", sootClass.getType());
		 
		 //java.lang.Object[] temp$0;
//	    Local temp$0 = Jimple.v().newLocal("temp$0", ArrayType.v(RefType.v("java.lang.Object"), 1));
//	    //temp$0 = newarray (java.lang.Object)[2];
//	    Stmt temp$0Stmt = Jimple.v().newIdentityStmt(temp$0, Jimple.v().newNewArrayExpr(RefType.v("java.lang.Object"), IntConstant.v(2)));
		    
	    
//		 Local oLocal = Jimple.v().newLocal("o", RefType.v("java.lang.Object"));
//		 Local temp0 = Jimple.v().newLocal("temp$0", BooleanType.v());
//		 Local temp1 = Jimple.v().newLocal("temp$1", BooleanType.v());
//		 
//		 Stmt stmt = Jimple.v().newIdentityStmt(thisLocal, Jimple.v().newThisRef(sootClass.getType()));
//		 units.add(stmt);
//		 stmt = Jimple.v().newIdentityStmt(oLocal, Jimple.v().newParameterRef(RefType.v("java.lang.Object"), 0));
//		 units.add(stmt);
         
		
	}
	
	
	public static synchronized void addFactoryMethodCreateObjectUtil(SootMethod constructor, String className) {
		
		if(constructor.getParameterCount()==0) {
			addZeroArgumentConstructor(constructor);
		}else {
			addMultipleArgumentConstructor(constructor, className);
		}
		
	}
	public static synchronized void addFactoryMethodCreateObject(String className) {
		
		SootClass sootClass = Scene.v().loadClassAndSupport(className);
		
        for (SootMethod method : sootClass.getMethods()) {
            if (method.isConstructor()) {
            	addFactoryMethodCreateObjectUtil(method, className);
            	System.out.println("Method:" + method);
            	
            }
        }
        
	}
	
	public static synchronized void addMethodHashCode(String className) {
		
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

	

