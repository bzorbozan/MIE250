
public class SDGSS extends SteepestDescent {
	
	private final double _PHI_ = (1. + Math.sqrt(5))/2.;
	private double maxStep;		// GSS max step size
	private double minStep;		// GSS min step size 
	private double delta;		// GSS delta parameter
	
	// constructors
	public SDGSS ()
	{
		super();
		this.maxStep = 1.0;
		this.minStep = 0.001;
		this.delta = 0.001;
	}
	public SDGSS(double maxStep , double minStep , double delta)
	{
		super();
		this.maxStep = maxStep;
		this.minStep = minStep;
		this.delta = delta;
	}
	
	// getters
	public double getMaxStep() {return this.maxStep; }
	public double getMinStep() {return this.minStep; }
	public double getDelta() {return this.delta; }
	
	// setters
	public void setMaxStep(double a) {this.maxStep = a; }
	public void setMinStep(double a) {this.minStep = a; }
	public void setDelta(double a) {this.delta = a; }
	
	// other methods
	
	// Note 2 Self: i think u set up the looping math in GSS and call it in linesearch ?????
	
	@Override
	public double lineSearch(Polynomial P, double [] x) // step size from GSS
	{
		if (this.getMaxStep() - this.getMinStep() == 0) {return this.getMaxStep(); }
		// a = min step	+	b = max step+	c =  calc w golden ratio+
		// current point +	polyn p	+		direction +
		if (this.getMaxStep() - this.getMinStep() <= this.getDelta()) 
		{
			double [] ssA = new double [x.length];		// step size = A
			double [] ssB = new double [x.length];
			
			for (int i=0; i < x.length; i++)
			{
				ssA[i] = x[i] + this.getMinStep() * this.direction(P, x)[i];
				ssB[i] = x[i] + this.getMinStep() * this.direction(P, x)[i];
			}
		}
		
		double c = this.getMinStep() + ((this.getMaxStep()-this.getMinStep())/this._PHI_);
		double [] dir = new double [x.length];
		dir = super.direction(P, x);		// you need an SD object!!
		return GSS(this.getMinStep(), this.getMaxStep(), c, x, dir, P);
	}
	
	@Override
	public boolean getParamsUser() // get algorithm parameters from user
	{
		System.out.println("Set parameters for SD with a golden section line search:");
		double ask_maxStep = Pro5_zorbozan.getDouble("Enter GSS maximum step size (0 to cancel): ", 0.00, Double.MAX_VALUE);
		if (ask_maxStep == 0)
		{
			System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
			return false;
		}
		else
		{
			double ask_minStep = Pro5_zorbozan.getDouble("Enter GSS minimum step size (0 to cancel): ", 0.00, ask_maxStep); // find max value
			
			if (ask_minStep == 0)
			{
				System.out.print("\nProcess canceled. No changes made to algorithm parameters.\n");
				return false;
			}
			else
			{
				double ask_delta = Pro5_zorbozan.getDouble("Enter GSS delta (0 to cancel): ", 0.00, Double.MAX_VALUE);
				if (ask_delta == 0)
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
						this.setMinStep(ask_minStep);
						this.setDelta(ask_delta);
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

	@Override
	public void print()
	{
		System.out.println("SD with a golden section line search:");
		super.print();
		System.out.format("GSS maximum step size: %f\n", this.getMaxStep());
		System.out.format("GSS minimum step size: %f\n", this.getMinStep());
		System.out.format("GSS delta: %f\n", this.getDelta()); 
	}
	
	private double GSS(double a, double b, double c, double [] x, double [] dir, Polynomial P)
	{
		// a: LB , b: UB , c: initial pt , x: current pt , dir: direction???
		// f(x+ss*dir) --> ss: a,b,c or y
		
		double [] ssA = new double [x.length];		// step size = A
		double [] ssB = new double [x.length];		// step size = B
		double [] ssC = new double [x.length];		// ss = C
		
		for (int i = 0; i < x.length; i++)			
		{
			ssA[i] = x[i] + a * dir[i];
			ssB[i] = x[i] + b * dir[i];
			ssC[i] = x[i] + c * dir[i];
		}
		
		double fssA = P.f(ssA);
		double fssB = P.f(ssB);
		double fssC = P.f(ssC);
		
		if (b-a < this.getDelta()) {return (a+b)/2.0; }
		else
		{
			if(fssC < fssA && fssC < fssB) //  REGULAR CONDITION
			{
				if (c-a > b-c) // if right side is the big interval
				{
					double y_ac = a + (c - a) / this._PHI_;	// sample step size for ss interval a-c
					
					double [] ssY = new double [x.length];		// ss = y_ac
					
					// calculate x + ss
					for (int i = 0; i < x.length; i++)	{ssY[i] = x[i] + y_ac * dir[i]; }
					
					// calculate f(x+ss)
					
					double fssY = P.f(ssY);
					
					if (fssY < fssA && fssY < fssC)
					{
						// min is in this interval
											// a stays the same
						// newB = c;		// c becomes b
						// newC = y_ac;		// y_ac becomes c
						
						return this.GSS(a, c, y_ac, x, dir, P);
					}
					else
					{
						return this.GSS(y_ac, b, c, x, dir, P);
					}
				}
				
				else	// if the left side is the big interval
					{
						// we essentially know then it HAS to be here?? like we dont have to check again do w? 
						double y_cb = b - (b - c) / this._PHI_;	// sample point for interval c-b
						
						double [] ssY = new double [x.length];		// ss = y_ac
						
						// calculate x + ss
						for (int i = 0; i < x.length; i++)	{ssY[i] = x[i] + y_cb * dir[i]; }
						
						// calculate f(x+ss)
						double fssY = P.f(ssY);
						
						if (fssY < fssB && fssY < fssC)
						{
							// min is in this interval
												// a stays the same
							// newB = c;		// c becomes b
							// newC = y_ac;		// y_ac becomes c
							
						return this.GSS(c, b, y_cb, x, dir, P);
						}
						else
						{
						return this.GSS(a, y_cb, c, x, dir, P);
						}
					}
			}
			else 		// THE SPECIAL CONSIDERATIONS
			{
				if(fssA >= fssB) {return b;}
				else {return a; }
			}
			
		}
	}
} // SDGSS CLASS ENDS HERE
