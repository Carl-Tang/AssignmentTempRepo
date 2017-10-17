package se254.a4.q1;

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

		// TODO Assignment 4, question 1.

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
		System.out.println("Please enter enter the name of a class to get a reflection of the class");

		// load the class
		Class<?> c = null;
		Object ref = null;
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
					ref = c.newInstance();
					System.out.println("The class " + name + " has been successfully loaded.");
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

		}

	}

}
