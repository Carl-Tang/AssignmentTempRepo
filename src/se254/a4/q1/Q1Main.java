package se254.a4.q1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * SE254 Assignment 4 Question 1 Main. This class should be implemented to
 * contain the functionality described in the assignment handout. you may
 * implement additional classes if you wish, but this class should be the main
 * program entry point.
 */
public class Q1Main {

	private static Scanner sc = new Scanner(System.in);
	private static List<String> searchList;
	private final static String CUTTINGLINE = "======================================================";

	public static void main(String[] args) {

		// initialize a search list including an empty string and the package names in
		// which to find the class
		searchList = new ArrayList<String>();
		searchList.add("");
		for (Package p : Package.getPackages()) {
			searchList.add(p.getName() + ".");
		}

		// print out introduction to this console program
		System.out.println("254 Assignment 4 Question 1");
		System.out.println("Author Carl Tang");
		System.out.println(CUTTINGLINE);
		System.out.println("Please enter enter the name of a class to get a objlection of the class");

		// load the class
		Class<?> c = null;
		Object obj = null;
		while (true) {
			String name = sc.nextLine();
			// look for the class in all packages, store the fully qualified name founded in
			// a founded list
			List<String> foundedList = new ArrayList<String>();
			for (String p : searchList) {
				try {
					c = Class.forName(p + name);
					foundedList.add(c.getName());
				} catch (ClassNotFoundException e) {
				}
			}

			if (foundedList.isEmpty()) {
				System.out.println("Oops, the class " + name + " is not found.\n"
						+ "Please enter another valid class name or try to use the full name of the class including the package name.");
			} else if (foundedList.size() > 1) {
				System.out.println("There are more than one class named " + name + " found.");
				System.out.println(
						"Please choose the one you are looking for and enter the fully qualified name of the class.");
				System.out.println(CUTTINGLINE);
				// print out the fully qualifies names of the classes found
				for (String found : foundedList) {
					System.out.println(found);
				}
				System.out.println(CUTTINGLINE);
			} else {
				// load the class if only one class is found
				try {
					obj = c.newInstance();
					System.out.println("The class " + c.getName()
							+ " has been successfully loaded and a new instance has been created.");
					break;
				} catch (InstantiationException | IllegalAccessException e) {
					System.out.println("Sorry, it seems that a new instance of this class cannot be constructed.");
					System.out.println("Please make sure the class " + name
							+ " has a public constructor which takes no parameter and try again.");
				}
			}
		}

		// let user modify the instance created
		while (true) {
			Field[] fields = c.getDeclaredFields();
			List<java.lang.reflect.Method> methods = new ArrayList<Method>();
			for (Method m : c.getDeclaredMethods()) {
				if (m.getParameterCount() == 0) {
					methods.add(m);
				}
			}

			// list all fields
			System.out.println("\n");
			System.out.println("The fields in the instance:");
			System.out.println(CUTTINGLINE);
			for (Field f : fields) {
				try {
					System.out.println(f.getName() + " = " + f.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			}
			System.out.println(CUTTINGLINE + "\n");

			// list all methods and assign a number to each method
			System.out.println("Methods exist in the class.");
			System.out.println(CUTTINGLINE);
			for (int i = 0; i < methods.size(); i++) {
				System.out.println(i + ". " + methods.get(i).getName());
			}
			System.out.println(CUTTINGLINE);

			// ask for a number to invoke the corresponding method
			int re;
			while (true) {
				System.out.println("Please choose a number between 0 and " + (methods.size() - 1)
						+ " to execute the corresponding method in this instance.");
				try {
					re = Integer.parseInt(sc.nextLine());
					if (re >= 0 && re < methods.size()) {
						break;
					} else {
						System.out.println("Please enter a number in the given range.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Please enter a number.");
				}

			}

			// invoke the corresponding method
			try {
				methods.get(re).invoke(obj, null);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}

			// give user info that the method has been invoked
			System.out.println("The method has been successfully invoked.");
			System.out.println(CUTTINGLINE + "\n");
		}

	}

}
