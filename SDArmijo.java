
public class SDArmijo extends SteepestDescent {

	private double maxStep; // Armijo max step size
	private double beta; // Armijo beta parameter
	private double tau; // Armijo tau parameter
	private int K; // Armijo max no. of iterations
	
	// constructors
	public SDArmijo()
	{
		super();
		this.maxStep = 1.0;
		this.beta = 0.0001;
		this.tau = 0.5;
		this.K = 10;
	}
	public SDArmijo(double maxStep, double beta, double tau, int K) 
	{
		super();
		this.maxStep = maxStep;
		this.beta = beta;
		this.tau = tau;
		this.K = K;
	}
	// getters
	public double getMaxStep() {return this.maxStep; }
	public double getBeta() {return this.beta; }
	public double getTau() {return this.tau; }
	public int getK() {return this.K; }
	
	// setters
	public void setMaxStep(double a) {this.maxStep = a; }
	public void setBeta(double a) {this.beta = a; }
	public void setTau(double a) {this.tau = a; }
	public void setK(int a) {this.K = a; }
	
	// other methods
	@Override
	public double lineSearch(Polynomial P, double [] x) // Armijo line search THE MAIN BIT
	{
		
		double alpha = this.getMaxStep();	 // initialize alpha
		int iter = 0;						// initialize # of iterations
		
		double[] X = new double[x.length];		// for LHS, to be placed into P.f(X)
		
		while (iter < this.getK())
		{
			// compute LHS
			for (int l=0; l<x.length; l++) {X[l] = x[l] - alpha * P.gradient(x)[l]; }
			double lhs = P.f(X);
			
			// compute RHS
			double rhs = P.f(x) - alpha * this.getBeta() * Math.pow(P.gradientNorm(x),2);
			
			if (lhs <= rhs || Double.isNaN(lhs) || Double.isNaN(rhs)) {return alpha; }							// condition added later. 
			
			alpha = this.getTau() * alpha; 
			iter ++;
			
		}
		System.out.println("Armijo line search did not converge!");
		return -1;
	}
	
	@Override
	public boolean getParamsUser() 
	{
		System.out.println("Set parameters for SD with an Armijo line search:");
		double ask_maxStep = Pro5_zorbozan.getDouble("Enter Armijo max step size (0 to cancel): ", 0.00, Double.MAX_VALUE);
		if (ask_maxStep == 0)
		{
			System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}
		else // only gets here if no 0s are entered. 
		{
			double ask_beta = Pro5_zorbozan.getDouble("Enter Armijo beta (0 to cancel): ", 0.00, 1.00);
			if (ask_beta == 0)
			{
				System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
				return false;
			}
			else
			{
				double ask_tau = Pro5_zorbozan.getDouble("Enter Armijo tau (0 to cancel): ", 0.00, 1.00); // CHECK BOUNDS!!!
				if (ask_tau == 0)
				{
					System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
					return false;
				}
				else
				{
					int ask_K = Pro5_zorbozan.getInteger("Enter Armijo K (0 to cancel): ",1, 1000);
					if (ask_K == 0)
					{
						System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
						return false;
					}
					else
					{
						boolean regSDset = super.getParamsUser();
						if (regSDset)
						{
							this.setMaxStep(ask_maxStep);
							this.setBeta(ask_beta);
							this.setK(ask_K);
							System.out.println("\n" + "Algorithm parameters set!"); // you can remove the last \n if you do a general +\n before each menu call (after the initial menu call. 
							return true;
						}
						else
						{
							return false;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void print()
	{
		System.out.println("SD with an Armijo line search:");
		super.print();
		System.out.format("Armijo maximum step size: %f\n", this.getMaxStep());		// check if it always formats it like this or if it's just the default.
		System.out.format("Armijo beta: %f\n", this.getBeta());
		System.out.format("Armijo tau: %f\n", this.getTau());
		System.out.format("Armijo maximum iterations: %d\n", this.getK());
	}
}
