

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Pro5_zorbozan {

	public static BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
	
	@SuppressWarnings("rawtypes") // ?? Not sure what this does
	public static void main(String[] args) throws IOException, NumberFormatException, FileNotFoundException {
	
		boolean main_valid; // this will check for the repetition of the menu & all of its options
		
		//SteepestDescent SD = new SteepestDescent();	//
		
		SDFixed SDF = new SDFixed();
		SDArmijo SDA = new SDArmijo();
		SDGSS SDG = new SDGSS();
		
		boolean firstRun = true; // to b updated at the end of 1st menu run. 
		boolean polyLoaded = false; // init as no polynomials loaded. 
		ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>(); //initialize here so that it is 
		
		do {
			main_valid = false;
			spaceBefMenu(firstRun); // prints a space before the menu if not the 1st time running it. 
			displayMenu();
			
			String menu_choice = cin.readLine();
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			if (menu_choice.equals("L") || menu_choice.equals("l")) 
			{
				polyLoaded = (loadPolynomialFile(polynomials));
				
				// if we change the polynomial, then the previously run results become irrelevant.
				if (polynomials.size() != 0) {polyLoaded = true; }  // evaluate to true (there is sth to run) IF previous polys exist
				// if first time it does it anyways
				main_valid = true; 	// menu gets repeated.
			}
					
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("F") || menu_choice.equals("f")) 
			{
				//if (P.isSet()) 
				if(polyLoaded) 
				{
					printPolynomials(polynomials);
				}
				else // if menu option E is never called. 
				{
					System.out.print("\nERROR: No polynomial functions are loaded!\n"); //check for the spacing of \n at the end. 
				}
				main_valid = true; 	// repeat menu regardless. 
			}
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("C") || menu_choice.equals("c")) 
			{
				// FIGURE OUT A WAY TO CLEAR THE POLYN ARRAY
				polynomials.clear();
				polyLoaded = false;
				SDF.setHasResults(false);	// if we clear the polys then previous results irrelevant.
				SDA.setHasResults(false);
				SDG.setHasResults(false);
				System.out.print("\nAll polynomials cleared.\n");
				main_valid = true; 	// repeat menu regardless. 
			}
						
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("S") || menu_choice.equals("s")) 
			{
					System.out.println();
					SDF.getParamsUser();
					System.out.println();
					SDA.getParamsUser();
					System.out.println();
					SDG.getParamsUser();
					
					// if the parameters change, the previous results become irrelevant.
					SDF.setHasResults(false);
					SDA.setHasResults(false);
					SDG.setHasResults(false);
					
				main_valid = true;	// repeat menu regardless.
			}
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("P") || menu_choice.equals("p")) 
			{
				SDF.print();			// default is printed if the user hasn't entered anything. 
				System.out.println();
				SDA.print();
				System.out.println();
				SDG.print();
				
				main_valid = true; 
			}
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("R") || menu_choice.equals("r")) 
			{ 
				if (polyLoaded || polynomials.size() != 0)
				{
					System.out.println();
					System.out.println("Running SD with a fixed line search:");
					SDF.init(polynomials);
					for(int i = 0; i < polynomials.size(); i++) 
						{SDF.run(i, polynomials.get(i)); }
					System.out.println();
					
					System.out.println("Running SD with an Armijo line search:");
					SDA.init(polynomials);
					for(int i = 0; i < polynomials.size(); i++) 
						{SDA.run(i, polynomials.get(i)); }
					System.out.println();
					
					System.out.println("Running SD with a golden section line search:");
					SDG.init(polynomials);
					for(int i = 0; i < polynomials.size(); i++) 
						{SDG.run(i, polynomials.get(i)); }
					System.out.println();
					
					System.out.println("All polynomials done.");									// should there be another new line at the end of this?
				}
				else
				{
					System.out.print("\nERROR: No polynomial functions are loaded!\n");
				}

				main_valid = true;
			}
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("D") || menu_choice.equals("d")) 
			{
				if(SDF.hasResults() && SDA.hasResults() && SDG.hasResults()) // if they have run the algor. before
				{
					// fixed
					
					System.out.println();
					System.out.println("Detailed results for SD with a fixed line search:");
					SDF.printAll();
					System.out.println(); //check if the empty space works. 
					System.out.println("Statistical summary for SD with a fixed line search:");
					SDF.printStats(); //has a \n after the best point
					
					System.out.println();
					System.out.println();
					
					// armijo
					
					System.out.println("Detailed results for SD with an Armijo line search:");
					SDA.printAll();
					System.out.println(); //check if the empty space works. 
					System.out.println("Statistical summary for SD with an Armijo line search:");
					SDA.printStats(); //has a \n after the best point
					
					System.out.println();
					System.out.println();
					
					// gss
					
					System.out.println("Detailed results for SD with a golden section line search:");
					SDG.printAll();
					System.out.println(); //check if the empty space works. 
					System.out.println("Statistical summary for SD with a golden section line search:");
					SDG.printStats(); //has a \n after the best point
				}
				else
				{
					System.out.println("\nERROR: Results do not exist for all line searches!");
				}
				main_valid = true;
			}
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("X") || menu_choice.equals("x")) 
			{
				if (SDF.hasResults() && SDA.hasResults() && SDG.hasResults())
				{compare(SDF,  SDA,  SDG);	}
				else {System.out.println("\nERROR: Results do not exist for all line searches!"); }
				
				main_valid = true; // repeats the menu}
			}
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else if (menu_choice.equals("Q") || menu_choice.equals("q")) 
			{
				System.out.print("\nArrivederci.");
				main_valid = false;
			}
			
			// ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
			
			else 
			{
				System.out.print("\nERROR: Invalid menu choice!\n");
				main_valid = true;
			}
			firstRun = false; // no longer the first run of alg.  
			} while(main_valid);
		
	} // main
		
	
	
	public static void displayMenu() 
	{
		System.out.print("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)\n"
				+ "L - Load polynomials from file\n"
				+ "F - View polynomial functions\n"
				+ "C - Clear polynomial functions\n"
				+ "S - Set steepest descent parameters\n"
				+ "P - View steepest descent parameters\n"
				+ "R - Run steepest descent algorithms\n"
				+ "D - Display algorithm performance\n"
				+ "X - Compare average algorithm performance\n"
				+ "Q - Quit\n"
				+ "\n"
				+ "Enter choice: ");
	}
	
	public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException
	{
		int newPolys = 0;												// DELETE?
		System.out.print("\nEnter file name (0 to cancel): ");					
		String filename = cin.readLine();
		
		if (filename.equals("0")) 
		{
			System.out.println("\nFile loading process canceled.");
			return false;														// returning false means no polynomials were loaded. 
		}
		// check file exceptions
		File file = new File(filename);
		// try catch file not found exception
		
		
		if (!(file.exists()) | !(file.canRead()) )
		{
			System.out.println("\nERROR: File not found!");
			return false;
		}
		
		//Actual bit
		BufferedReader fin = new BufferedReader(new FileReader(filename));		
		
		String line = " ";
		 
		ArrayList<double[]> polyArrayList = new ArrayList<double[]>(); 				// all variables and coefficients for one variable.
		int polyNo = 0;
	
		do {
			line = fin.readLine(); 	
			
			// * or null (poly creation)
			
			if (line == null || line.equals("*") )
			{
				polyNo++;
				boolean isPolyValid = true;
				int polyDegree = 0;
				
				// check for validity
				
				if(polyArrayList.size() != 0)
				{
					polyDegree = polyArrayList.get(0).length - 1 ;			// updates it to length from coef line1
					
					for (double[] polyCoefs : polyArrayList)
					{
						if (polyDegree != polyCoefs.length-1) 
						{
							System.out.printf("\nERROR: Inconsistent dimensions in polynomial %d!\n", polyNo);
							isPolyValid = false;
							break;
						}
					}
				}
				
				// if valid, create poly
				
				if(isPolyValid)
				{
					newPolys++;
					double [][] polyArray = new double [polyArrayList.size()][polyDegree];	
					for(int i=0; i < polyArrayList.size(); i++)	{polyArray[i] = polyArrayList.get(i); }
					Polynomial Poly = new Polynomial(polyArrayList.size(), polyDegree, polyArray);
					P.add(Poly);
				}
				
				polyArrayList.clear();
				
				if (line == null) break;	// exit the do-while. u done.
			}
			
			// regular coefs line
			
			else if (line != null)
			{	// only enters here if it is a coefficients line.
				String[] splitString = line.split(","); 																// new for each line.						
				double [] doubleCoefs = new double[splitString.length];													// length acc to each line. 
				for (int i=0; i < splitString.length; i++) {doubleCoefs[i] = Double.parseDouble(splitString[i]); }		// change string csv's to doubles 
				polyArrayList.add(doubleCoefs);																			// add to the poly info storage array
			}
		} while(true);
		
		fin.close();
		
		
		
		System.out.printf("\n%d polynomials loaded!\n", newPolys);					// P.size() gives u how many polyns are in the actual array. (only valid ones are added)
		return newPolys != 0;		// true: a polyn was added.  
	
	}
	
	public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG)
	{
		SDF.getParamsUser();
		SDA.getParamsUser();
		SDG.getParamsUser();
	}
	
	public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG)
	{
		System.out.println();
		SDF.print();
		System.out.println();
		SDA.print();
		System.out.println();
		SDG.print();
	}
	
	public static void runAll(SDFixed SDF, SDArmijo SDA,SDGSS SDG,ArrayList<Polynomial> P)
	{
		for (int i = 0; i < P.size(); i++)
		{
			System.out.println("Running SD with a fixed line search:");
			SDF.run(i, P.get(i));
			System.out.println("Running SD with an Armijo line search:");
			SDA.run(i,  P.get(i));
			System.out.println("Running SD with a golden section line search:");
			SDG.run(i, P.get(i));
		}
	}
	
	public static void printPolynomials(ArrayList<Polynomial> P) 
	{
		//if (P.size() > 0) // not needed bc u error check for this in the menu. 
		//{
		// print header
		System.out.println("\n---------------------------------------------------------\n"
				+ "Poly No.  Degree   # vars   Function\n"
				+ "---------------------------------------------------------");
		for (int i=0; i<P.size(); i++) 															// loop through the polynomial array
		{
			System.out.printf("%8d%8d%9d   ", i+1, P.get(i).getDegree(),P.get(i).getN());
			P.get(i).print();	// prints the polynomial's long version.
			//System.out.println();
		}
		//}
		
	}
	
	public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P)
	{
		System.out.println("Detailed results for SD with a fixed line search:");
		SDF.printAll();
		System.out.println("Statistical summary for SD with a fixed line search:");
		SDF.printStats();
		System.out.println("Detailed results for SD with an Armijo line search:");
		SDA.printAll();
		System.out.println("Statistical summary for SD with an Armijo line search:");
		SDA.printStats();
		System.out.println("Detailed results for SD with a golden section line search:");
		SDG.printAll();
		System.out.println("Statistical summary for SD with a golden section line search:");
		SDA.printStats();
	}
	
	public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG)
	{
		// print everything out for the user to see
		
		System.out.println("\n"
				+ "---------------------------------------------------\n"
				+ "          norm(grad)       # iter    Comp time (ms)\n"
				+ "---------------------------------------------------");
		System.out.print("Fixed  ");
		double[] AvrgSDF = SDF.calcPrintAvrgRow();
		System.out.print("Armijo ");
		double[] AvrgSDA = SDA.calcPrintAvrgRow();
		System.out.print("GSS    ");
		double[] AvrgSDG = SDG.calcPrintAvrgRow();
		System.out.print("---------------------------------------------------\n"
				+ "Winner");
		
		// now, we conduct the actual comparison (smallest wins in all categories)
		
		// norm grad comparison
		double minNormGrad = AvrgSDF[0];
		String normGradWinner = "Fixed";
		if (minNormGrad > AvrgSDA[0]) {normGradWinner = "Armijo"; }
		else if (minNormGrad > AvrgSDG[0]) {normGradWinner = "GSS"; }
		System.out.format("%14s", normGradWinner);
		
		// # iter comparison
		double minIter = AvrgSDF[1];
		String iterWinner = "Fixed";
		if (minIter > AvrgSDA[1]) {iterWinner = "Armijo"; }
		else if (minIter > AvrgSDG[1]) {iterWinner = "GSS"; }
		System.out.format("%13s", iterWinner);
		
		// comp time comparison
		double minCompTime = AvrgSDF[2];
		String compTimeWinner = "Fixed";
		if (minCompTime > AvrgSDA[2]) {compTimeWinner = "Armijo"; }
		else if (minCompTime > AvrgSDG[2]) {compTimeWinner = "GSS"; }
		System.out.format("%18s", compTimeWinner);
		
		System.out.print("\n---------------------------------------------------\n"
				+ "Overall winner: ");
		if (normGradWinner.equals(compTimeWinner) && compTimeWinner.equals(iterWinner)) {System.out.print(normGradWinner + "/n"); }
		else {System.out.print("Unclear\n");}
	}
	
	public static int getInteger(String prompt, int LB, int UB)   
	{
		// prompt is what u want to display to the user as u ask for input (at times repeatedly).
		int no = 0; //declare no here so that it isnt a local variable within the try statement
		boolean valid;
		
		// condition for infinity
		String bound1;
		String bound2;
		
		if (LB == - Integer.MAX_VALUE){bound1 = "-infinity";}
		else {bound1 = String.format("%d", LB);}
		
		if (UB == Integer.MAX_VALUE){bound2 = "infinity";}
		else {bound2 = String.format("%d", UB);}
		
		do {
			valid = true;
			
			try {
				System.out.print(prompt); //not println bc in some cases we might want to deactivate printing into a new line. -> esp in menu choice option. 
				no = Integer.parseInt(cin.readLine()); //assign value to no. 
				if (no < LB || no > UB) { //check against the upper bounds. 
					System.out.print("ERROR: Input must be an integer in ["+ bound1 + ", " + bound2 + "]!\n");
					valid = false;
				}
			} catch(NumberFormatException e) {
				System.out.print("ERROR: Input must be an integer in ["+ bound1 + ", " + bound2 + "]!\n");
				valid = false;
			} catch (IOException e) {
				System.out.print("ERROR: Input must be an integer in ["+ bound1 + ", " + bound2 + "]!\n");
				valid = false;
			} //io exception catch closing. 
			if(!valid)
				System.out.println();
			
			}while(!valid);
		return no;
	}
	
	public static double getDouble(String prompt, double LB, double UB)  
	{
		double no = 0; //declare no here so that it isnt a local variable within the try statement
		boolean valid;
		
		// condition for infinity
		String bound1;
		String bound2;
		
		if (LB == - Double.MAX_VALUE){bound1 = "-infinity";}
		else {bound1 = String.format("%.2f", LB);}
		
		if (UB == Double.MAX_VALUE){bound2 = "infinity";}
		else {bound2 = String.format("%.2f", UB);}
		
		do {
			valid = true;
			
			try {
				System.out.print(prompt); //not println bc in some cases we might "want to deactivate printing into a new line. -> esp in menu choice option. 
				no = Double.parseDouble(cin.readLine()); //assign value to no. 
				if (no < LB || no > UB) { //check against the upper bounds. 
					System.out.print("ERROR: Input must be a real number in ["+ bound1 + ", " + bound2 + "]!\n");
					valid = false;
				}
			} catch(NumberFormatException e) {
				System.out.print("ERROR: Input must be a real number in ["+ bound1 + ", " + bound2 + "]!\n");
				valid = false;
			} catch (IOException e) {
				System.out.print("ERROR: Input must be a real number in ["+ bound1 + ", " + bound2 + "]!\n"); 
				valid = false;
			} //io exception catch closing. 
			if(!valid)
				System.out.println();
			
			}while(!valid);
		return no;
	}
	// extra function ( i added this bc i found it would be useful )
	public static void spaceBefMenu(boolean firstRun)		// print empty line if not the first time running the menu. 
	{
		if(!firstRun) {System.out.println(); }
	}
	
	
} // class


