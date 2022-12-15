
public class SDFixed extends SteepestDescent {
	private double alpha; // fixed step size
	
	// constructors
	public SDFixed ()
	{
		super();
		this.alpha = 0.01; // check default value 
		
	}
	public SDFixed(double alpha)
	{
		super();
		this.alpha = alpha;
	}
	
	// getters
	public double getAlpha () {return this.alpha; }
	
	// setters
	public void setAlpha(double a) 
	{
		this.alpha = a;
	}
	
	// other methods
	@Override
	public double lineSearch(Polynomial P, double [] x) { return this.getAlpha(); }  // fixed step size
	
	@Override
	public boolean getParamsUser()	// the vanilla option 	DOES THIS OVERRIDE. I DONT WANT IT TO? HOW DO I BOTH INCLD. THE FUNC HERE BUT NOT OVERRIDE THE PARENT?
	 {
		System.out.println("Set parameters for SD with a fixed line search:");
		boolean gotParams = super.getParamsUser(); 
		if (gotParams) 
		{
			System.out.println("\n" + "Algorithm parameters set!"); 
			return true;
		}
		return false;
	 }	
	
	@Override
	public void print() 
	{	// do i have to override it f it is calling from the parent? to make sure that it doesnt cause any problems. 
		System.out.println("SD with a fixed line search:");
		super.print();
		System.out.format("Fixed step size (alpha): %.2f\n", alpha);		//or do we double to string tjis as well?
	}

}
