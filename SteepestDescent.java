import java.util.Arrays;
import java.util.ArrayList;

public class SteepestDescent {
	
	private double eps;							// tolerance											// stopping criteria
	private int maxIter;						// maximum number of iterations							// stopping criteria
	
	// U HAVE TO REMOVE STEP SIZE FROM SD. IT WILL NO LONGER BE HERE, BUT ONLY EXIST IN CHILD
	// TO DO: MOVE RUN() INTO FIXED SD?
	
	private double x0;							// starting point
	private ArrayList<double[]> bestPoint;		// best point found for all polynomials
	private double[] bestObjVal;				// best obj fn value found for all polynomials
	private double[] bestGradNorm;				// best gradient norm found for all polynomials
	private long[] compTime;					// computation time needed for all polynomials
	private int[] nIter;						// no. of iterations needed for all polynomials
	private boolean resultsExist;				// whether or not results exist							// each time you load new polynomial set this to zero.
	
	// constructors
	public SteepestDescent()					// TO DO, JOSH?: CHECK IF THESE HAVE CHANGED. 
	{
		this.eps = 0.001;
		this.maxIter = 100;
		this.x0 = 1.0;
		this.resultsExist = false;
	}
	public SteepestDescent(double eps, int maxIter, double stepSize, double x0)
	{
		this.eps = eps;
		this.maxIter = maxIter;
		this.x0 = x0; 														
		this.resultsExist = false;
	}

	// getters
	public double getEps() {return this.eps; }
	public int getMaxIter() {return this.maxIter; }
	public double getX0() {return this.x0; }
	public double[] getBestObjVal() {return this.bestObjVal; }
	public double[] getBestGradNorm() {return this.bestGradNorm; }
	public double [] getBestPoint(int i) {return this.bestPoint.get(i); }		// best point for i-th polynomial.
	public int[] getNIter() {return this.nIter; }
	public long[] getCompTime() {return this.compTime; } 					
	public boolean hasResults() {return this.resultsExist; }
	
	// setters
	public void setEps(double a) {this.eps = a; }
	public void setMaxIter(int a) {this.maxIter = a; }
	public void setX0(double a) {this.x0 = a; }											
	public void setBestObjVal(int i, double a) {this.bestObjVal[i] = a; }					
	public void setBestGradNorm(int i, double a) {this.bestGradNorm[i] = a; }
	public void setBestPoint(int i, double[] a) {this.bestPoint.add(i, a); }	 
	public void setCompTime(int i, long a)	{this.compTime[i] = a; }				
	public void setNIter(int i, int a) {this.nIter[i] = a; }
	public void setHasResults(boolean a) {this.resultsExist = a; }
	
	// other methods
	public void init(ArrayList <Polynomial > P)							// initialize member arrays to correct size											
	{ 
		// P.size() gives me #polynomials
		bestPoint = new ArrayList<double[]>(); 
		bestObjVal = new double [P.size()];
		bestGradNorm = new double [P.size()];
		compTime = new long [P.size()];
		nIter = new int [P.size()];
	}
	
	// LINESEARCH HAS TO BE CHANGED!!! (a) you will no longer have step size, (b) you will have to override it in your special child functions.
	// the inside of linesearch doesn't matter, it will be overriden by every child.
	public double lineSearch(Polynomial P, double[] x) {return 1; }		 // find the next step size
	
	public double[]  direction(Polynomial P, double[] x) 				// find the next direction (x is the current point i presume?)
	{
		double[] direction = new double[P.getN()]; // init. storage
		double [] gradient = P.gradient(x);  // result from the gradient func
		
		for (int i = 0; i < P.getN(); i++) //for loop to turn the gradient results negative.
		{
			direction[i] = -1 * gradient[i];
		}
		return direction;
	}
	
	public boolean getParamsUser() 										// get parameters from user (adjusted)
	{
		//this.init();
		double ask_eps = Pro5_zorbozan.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.00, Double.MAX_VALUE);
		
		if (ask_eps == 0)
		{
			System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}
		else		// not cancelled
		{
			int ask_maxIter = Pro5_zorbozan.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000);
			if (ask_maxIter == 0)
			{
				System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
				return false;
			}
			else
			{
				double ask_x0 = Pro5_zorbozan.getDouble("Enter value for starting point (0 to cancel): ", -Double.MAX_VALUE, Double.MAX_VALUE);
				
				if (ask_x0 == 0)
				{
					System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
					return false;
				}
				else 
				{
					// set the parameters at the very end. 
					setEps(ask_eps);
					setMaxIter(ask_maxIter);
					setX0(ask_x0);
					return true;
				}	
			}
		}
	}

	public void print()													// print algorithm parameters
	{
		System.out.println("Tolerance (epsilon): " + Double.toString(this.getEps()));
		System.out.format("Maximum iterations: %d\n", this.getMaxIter());
		//System.out.println("Step size (alpha): " + Double.toString(this.getStepSize()));
		System.out.println("Starting point (x0): "+ Double.toString(this.getX0()));
		
		//System.out.println(); // print a new line (so that the menu prints correctly) idt this is necessarry
	}

	public void printSingleResult(int i, boolean rowOnly)				// print iteration results, column header optional
	{
		//testing NaN
		//System.out.println("Coords of N");
		//for (double ele : this.getBestGradNorm()){System.out.println(ele);}
		//System.out.println("N coords done");
		
		String bestPointCoord = "";
		
		// create a string with the correct formatting for the best point
		for (int s = 0; s < this.getBestPoint(i).length - 1; s++) 		// 
		{
			bestPointCoord += String.format("%.4f, ", this.getBestPoint(i)[s]);
		}
			bestPointCoord += String.format("%.4f", this.getBestPoint(i)[this.getBestPoint(i).length - 1]); // last one is formatted seperately since it doesn't have the comma a the end. 
		
		if (rowOnly == true)
		{
			System.out.format("%8d%13.6f%13.6f%9d%17d   ", i+1, this.getBestObjVal()[i], this.getBestGradNorm()[i], this.getNIter()[i], this.getCompTime()[i]);
			System.out.println(bestPointCoord); // has a /n at the end. RECALL: we format the best point seperately. 
		}
		else 	// if you are printing for the first time. 
		{
		
			System.out.print("-------------------------------------------------------------------------\n"
					+ "Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   \n"
					+ "-------------------------------------------------------------------------\n");
			System.out.format("%8d%13.6f%13.6f%9d%17d   ", i+1, this.getBestObjVal()[i], this.getBestGradNorm()[i], this.getNIter()[i], this.getCompTime()[i]);
			System.out.println(bestPointCoord); // RECALL: we format the best point seperately. 
		}
		// 10.6
		// 13.6
		// 9
		// 17.4
		// 13.4
	}
	
	public double[] calcPrintAvrgRow()		// extra method : prints average row, and stores averages in an array
	{
		System.out.format("%13.3f%13.3f%18.3f\n", Stats.average(this.getBestGradNorm()), Stats.average(this.getNIter()), Stats.average(this.getCompTime()));
		//double [] avrgRow = new double [3];
		
		// CHECK - JOSH: is this way to define it putting it randomly, or in the correct indices. 
		double [] avrgRow = {Stats.average(this.getBestGradNorm()), Stats.average(this.getNIter()), Stats.average(this.getCompTime())};
		
		return avrgRow;
	}
	
	public void printStats()											// print statistical summary of results
	{
		System.out.print("---------------------------------------------------\n"
				+ "          norm(grad)       # iter    Comp time (ms)\n"
				+ "---------------------------------------------------\n");
		
		// formatting
		System.out.format("Average%13.3f%13.3f%18.3f\n"
				+ "St Dev %13.3f%13.3f%18.3f\n"
				+ "Min    %13.3f%13d%18d\n"
				+ "Max    %13.3f%13d%18d\n",				// decide whether you need a new line after this. 
		// calculate the statistics
				Stats.average(this.getBestGradNorm()), Stats.average(this.getNIter()), Stats.average(this.getCompTime()),
				Stats.standardDeviation(this.getBestGradNorm()), Stats.standardDeviation(this.getNIter()), Stats.standardDeviation(this.getCompTime()),
				Stats.minimum(this.getBestGradNorm()), Stats.minimum(this.getNIter()), Stats.minimum(this.getCompTime()),
				Stats.maximum(this.getBestGradNorm()), Stats.maximum(this.getNIter()), Stats.maximum(this.getCompTime()));
	}
	
	public void printAll()												// print final results for all polynomials
	{
		//int size = this.bestPoint.size();
		int size = this.getBestGradNorm().length;
		for (int i = 0; i < size; i++)					// i is counting the index of the polynomials (matches the printSingleResult function
		{
			if (i==0) {printSingleResult(i, false); }					// if it's the first poly, print the header. alternatively, just move this outside the loop so it doesnt get checked again and again (more efficient)
			else {printSingleResult(i, true); }
		}
	}
	
	public void run(int i, Polynomial P)								// correct this . i is for their index, not poly no
	{
		if(P.isSet())													// is this even necessary
		{
			// initialize #iterations as 0.
			this.setNIter(i,0);			
			
			// initialize comp time to 0.
			this.setCompTime(i,0);		
			
			double [] x_on = new double[P.getN()];
			
			// initialize x_on entries as x0
			for (int m = 0; m < P.getN(); m++) {x_on[m] = this.getX0(); }
			
			// initialize separate counter for iterations
			int iterNo = 0;
			
			// get current time
			long start = System.currentTimeMillis();
			
			
			do{	
				double [] dir = direction(P, x_on);
				
		
				if (lineSearch(P, x_on) == -1)		// this condition will only be satisfied if armijo did not converge. 
				{
					this.setBestPoint(i, x_on);
					this.setBestObjVal(i, P.f(x_on));
					this.setBestGradNorm(i, P.gradientNorm(x_on));
					this.setNIter(i, 1);
					this.setCompTime(i, 0);
					return;
				}
				
				
				if (P.gradientNorm(x_on) <= this.getEps() || iterNo == this.getMaxIter() || Double.isNaN(P.gradientNorm(x_on)))
				{
					//System.out.println(iterNo);
					// update member fields once done iterating

					// set time
					long elapsedTime = System.currentTimeMillis() - start;
					this.setCompTime(i, elapsedTime);

					// set "bests" (x, f(x), gradNorm(x))
					this.setBestPoint(i, x_on);
					this.setBestObjVal(i, P.f(x_on));
					this.setBestGradNorm(i, P.gradientNorm(x_on));

					// set no of iterations
					this.setNIter(i, iterNo); 

					// system now has results
					//this.setHasResults(true);
					
					break;
				}
				else
				{
					for (int m = 0; m < x_on.length; m++)
					{
						x_on[m] += lineSearch(P, x_on) * dir[m];			// arguments added to linesearch
					}
					iterNo ++;
				}
				
				this.setHasResults(true);
				
			}
			while (true);
			
			
			// print statement of completion
			System.out.printf("Polynomial %d done in %dms.\n", i+1, getCompTime()[i]);
		
		} // for P.isSet()
	}
	
	
} // steepest descent class

