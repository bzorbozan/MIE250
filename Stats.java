import java.math.*;
public class Stats {
	//min max same type as whatever u take in
	// for next project try and see if you can do this with generic types
	// careful: generic types only work for non-primitive types but the stuff you will actually pass in will be primitive type arrays. (arrays are non-primitive but still)
	// dont want to deal w this now tho so im just gonna overload. 
	
	// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ AVERAGE OVERLOADING ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
	
	public static double average(double[] list)
	{
		if (list.length != 0)
		{
			double sum = 0.0;			// initialize sum
			for (double element : list) {sum += element; }
			return (sum/list.length);
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
		
	}
	
	public static double average(int[] list)
	{
		if (list.length != 0)
		{
			double sum = 0.0;			// initialize sum
			for (int element : list) {sum += element; }
			return (sum/list.length);
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
	}
	
	public static double average(long[] list)
	{
		if (list.length != 0)
		{
			double sum = 0.0;			// initialize sum
			for (long element : list) {sum += element; }
			return (sum/list.length);
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
	}
	
	// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ STAND DEVIATION OVERLOADING ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
	
	public static double standardDeviation(double[] list)
	{
		if (list.length != 0)
		{
			double avrg = average(list);
			double sumOfSqrs = 0.0; // initialize SofS of the deviations from the mean
			for (double element : list) {sumOfSqrs += Math.pow(avrg - element, 2);}
			return Math.sqrt(sumOfSqrs/(list.length-1));
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
	}
	
	public static double standardDeviation(int[] list)
	{
		if (list.length != 0)
		{
			double avrg = average(list);
			double sumOfSqrs = 0.0; // initialize SofS of the deviations from the mean
			for (int element : list) {sumOfSqrs += Math.pow(avrg - element, 2);}
			return Math.sqrt(sumOfSqrs/(list.length-1));
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
	}
	
	public static double standardDeviation(long[] list)
	{
		if (list.length != 0)
		{
			double avrg = average(list);
			double sumOfSqrs = 0.0; // initialize SofS of the deviations from the mean
			for (long element : list) {sumOfSqrs += Math.pow(avrg - element, 2);}
			return Math.sqrt(sumOfSqrs/(list.length-1));
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
	}
	
	// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ MIN OVERLOADING ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
	
	public static double minimum(double[] list)
	{
		if (list.length != 0)
		{
			double min = list[0];
			for (double element : list) {if (element < min) {min = element; } }
			return min;
		}
		else 
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
		
	}
	
	public static int minimum(int[] list)
	{
		if (list.length != 0)
		{
			int min = list[0];
			for (int element : list) {if (element < min) {min = element; } }
			return min;
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678;
		}
		
	}
	
	public static long minimum(long[] list)
	{
		if (list.length != 0)
		{
			long min = list[0];
			for (long element : list) {if (element < min) {min = element; } }
			return min;
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678;
		}
	}
	
	// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ MAX OVERLOADING ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
	
	public static double maximum(double[] list)
	{
		if (list.length != 0)
		{
			double max = list[0];
			for (double element : list) {if (element > max) {max = element; } }
			return max;
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678.0;
		}
	}
	
	public static int maximum(int[] list)
	{
		if (list.length != 0)
		{
			int max = list[0];
			for (int element : list) {if (element > max) {max = element; } }
			return max;
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678;
		}
	}
	
	public static long maximum(long[] list)
	{
		if (list.length != 0)
		{
			long max = list[0];
			for (long element : list) {if (element > max) {max = element; } }
			return max;
		}
		else
		{
			System.out.println("List is empty");			// this is for personal error checking. 
			return -12345678;
		}
	}
	
}
