package se254.a4.q3;

import java.lang.reflect.*;
import java.util.*;

public class ExtractorTemplate
{
  protected static String nl;
  public static synchronized ExtractorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ExtractorTemplate result = new ExtractorTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "public class ";
  protected final String TEXT_2 = " {";
  protected final String TEXT_3 = NL + "\tpublic ";
  protected final String TEXT_4 = " ";
  protected final String TEXT_5 = ";";
  protected final String TEXT_6 = NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    	ExtractorArgument arg = (ExtractorArgument) argument; 
    	// get the name of the new class from the extractor argument 
    	String name = arg.className; 
     
    	// intiallize a map to store the name of the fields that existed in class a and  
    	// b along with the time they appear in the two classes 
    	Map<String, Integer> allFields = new HashMap<String, Integer>(); 
    	for (Field f : arg.a.getDeclaredFields()) { 
    		int i = (allFields.containsKey(f.getName()) ? 2 : 1); 
    		allFields.put(f.getName(), i); 
    	} 
    	for (Field f : arg.b.getDeclaredFields()) { 
    		int i = (allFields.containsKey(f.getName()) ? 2 : 1); 
    		allFields.put(f.getName(), i); 
    	} 
     
    	// check the field names that appear both in a and b 
    	// if the corresponding fields have the same type in two classes, 
    	// put the field in the list that contains fields to be extracted to the new class 
    	List<Field> exFields = new ArrayList<Field>(); 
    	for (String fName : allFields.keySet()) { 
    		if (allFields.get(fName).equals(2)) { 
    			try { 
    				Field fa = arg.a.getField(fName); 
    				Field fb = arg.b.getField(fName); 
    				if (fa.getType().equals(fb.getType())) { 
    					exFields.add(fa); 
    				} 
    			} catch (NoSuchFieldException | SecurityException e) {} 
    		} 
    	} 
     
    	// create the new class 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(name);
    stringBuffer.append(TEXT_2);
    	// extract the fields into the new class 
    	for (Field f:exFields) { 
    stringBuffer.append(TEXT_3);
    stringBuffer.append(f.getType().getSimpleName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(f.getName());
    stringBuffer.append(TEXT_5);
    	} 
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}
