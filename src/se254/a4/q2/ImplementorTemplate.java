package se254.a4.q2;

import java.lang.reflect.*;
import java.util.*;

public class ImplementorTemplate
{
  protected static String nl;
  public static synchronized ImplementorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ImplementorTemplate result = new ImplementorTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "public class ";
  protected final String TEXT_2 = "Implementation implements ";
  protected final String TEXT_3 = " {";
  protected final String TEXT_4 = NL + NL + "\t@Override" + NL + "\tpublic ";
  protected final String TEXT_5 = " ";
  protected final String TEXT_6 = "(";
  protected final String TEXT_7 = ") {";
  protected final String TEXT_8 = "}" + NL + "\t";
  protected final String TEXT_9 = NL + "}" + NL + "\t";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     Class c = (Class) argument; 
     
     // initialize a list of methods 
     List<Method> methods = new ArrayList<Method>(); 
     
     // add the methods of the given interface and its parent interfaces to the list 
     for (Method m1 : c.getMethods()) { 
     	boolean isSame = false; 
     	// check is the method already existed by checking the name, return type and 
     	// parameter types of the two methods 
     	for (Method m2 : methods) { 
     		// check name 
     		if (m1.getName().equals(m2.getName())) { 
     			// check return type 
     			if (m1.getReturnType().equals(m2.getReturnType())) { 
     				// check parameter types 
     				Set s1 = new HashSet(); 
     				Set s2 = new HashSet(); 
     				for (Class c1 : m1.getParameterTypes()) { s1.add(c1); } 
     				for (Class c2 : m2.getParameterTypes()) { s2.add(c2); } 
     				if (s1.equals(s2)) { isSame = true; } // true if all the checks return true 
     			} 
     		} 
     	} 
     	if (!isSame) { methods.add(m1); } 
     } 
     
    stringBuffer.append(TEXT_1);
    stringBuffer.append(c.getSimpleName());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(c.getSimpleName());
    stringBuffer.append(TEXT_3);
     
     for (Method m:methods) {
     	// get the return type of the method 
     	String rt = m.getReturnType().getSimpleName(); 
     
     	// get the parameters of the method 
     	String params = ""; 
     	Class[] pts = m.getParameterTypes(); 
     	for (int i = 0; i < pts.length; i++) { 
     		params = params + pts[i].getSimpleName() + " p" + (i + 1); 
     		// add a comma if the parameter is not the last one 
     		if (i != pts.length - 1) { params = params + ", "; } 
     	} 
     
     	// the return statement 
     	String rtstmt = " "; 
     	if (rt.equals("int") || rt.equals("double")) { rtstmt = " return 0; "; } 
     	else if (rt.equals("boolean")) { rtstmt = " return false; "; } 
     	else if ( rt.equals("void")) {rtstmt = " "; } 
     	else { rtstmt = " return null; "; } 
    stringBuffer.append(TEXT_4);
    stringBuffer.append(rt);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(m.getName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(params);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(rtstmt);
    stringBuffer.append(TEXT_8);
     } 
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}
