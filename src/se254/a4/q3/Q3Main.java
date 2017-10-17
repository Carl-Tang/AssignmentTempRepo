package se254.a4.q3;

/**
 * SE254 Assignment 4 Question 3 Main. Running this should generate and print out the C class as detailed in the
 * assignment handout.
 */
public class Q3Main {

	public static void main(String[] args) {

		ExtractorArgument extractorArg = new ExtractorArgument();
		extractorArg.a = A.class;
		extractorArg.b = B.class;
		extractorArg.className = "C";

		ExtractorTemplate template = new ExtractorTemplate();
		String result = template.generate(extractorArg);
		System.out.println(result);

	}

}
