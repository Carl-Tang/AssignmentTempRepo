package se254.a4.q3;

import java.lang.reflect.*;
import java.util.*;

public class Test {

	public static void main(String[] args) {
		ExtractorArgument arg = new ExtractorArgument();
		arg.className = "C";
		arg.a = A.class;
		arg.b = B.class;

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
		// put the field in the list that contains fields to be extracted to the new
		// class
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
		
		for (Field f:exFields) {
			f.getType().getSimpleName();
			f.getName();
		}

	}

}
