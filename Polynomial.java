
public class Polynomial {
	
	private int n;					// no. of variables
	private int degree;				// degree of polynomial
	private double [][] coefs;		// coefficients
	
	// constructors
	public Polynomial()				// empty polynomial (all zeros)
	{
		this.n = 0;
		this.degree = 0;
	}
	public Polynomial(int n, int degree, double[][] coefs)
	{
		this.n = n;				// no of variables. 
		this.degree = degree;
		this.coefs = coefs; // COME BACK: Will these create an issue while debugging? yes. pass by reference.
		
	}
	
	// getters
	public int getN() {return this.n; }
	public int getDegree() {return this.degree; }
	public double [][] getCoefs() {return this.coefs; }
	
	// setters
	public void setN(int a) {this.n = a; }
	public void setDegree(int a) {this.degree = a; }
	public void setCoef(int j, int d, double a) {this.coefs[j][d] = a; }
	
	// other methods
	
	public void init() 								// init member arrays to correct size
	{
		this.coefs = new double[this.n][this.degree+1];
	}
	
	public void print()								// print out the polynomial
	{
		System.out.printf("f(x) =");				// previously (lab3) printed "\nf(x) =" , but changed for lab4
		
		for(int i=0; i < this.getN(); i++)				// loop through the no. of variables. (from x1 --> xn)
		{
			System.out.printf(" (");
			
			for(int j = 0 ; j < this.getDegree(); j++)		// also loop through the degree of polyn.
			{											// j loops through indeces of inner array of coefs. 
				System.out.printf(" %.2fx%d^%d +", this.getCoefs()[i][j], i+1 ,this.getDegree() - j);
											//  coef			which x		power
			}
			System.out.printf(" %.2f )", this.getCoefs()[i][this.getDegree()]);
			
			if (i < this.n - 1) {System.out.printf(" +"); } // so it doesn't get printed on the last x
		}
		System.out.println();
	}
	
	public boolean isSet()							// indicate whether the polynomial is set.
	{
		if (this.getN() != 0 && this.getDegree() != 0)
			return true;  //bc u cant input 0 without leaving in the menu
		return false;
	}
	
	public double f(double [] x)					// calculate function value at point x
	{
		double f_x = 0; 		// acts like sum.
		for (int i = 0; i < this.getN(); i++)
		{
			for (int j = 0; j < this.getDegree() + 1; j++)
			{
				f_x += this.getCoefs()[i][j] * Math.pow(x[i], this.getDegree()-j);
			}
		}
		return f_x;
	}
	
	public double[] gradient(double[] x)			// calculate gradient at point x
	{
		double [] gradX = new double[this.getN()];	// init. storage
		
		// loop through to intialize each entry to zero. 
		for (int z = 0; z < this.getN(); z++) {gradX[z] = 0;}
		
		for (int i = 0; i < this.getN(); i++)
		{
			for (int j = 0; j < this.getDegree()+1; j++)
			{
				if ( (this.getCoefs()[i][j] == 0 || x[i] == 0) && j== this.getDegree()) {gradX[i] += 0;}
				else if (j == this.getDegree() - 1)							// special case, since we can get indeterminate form such as 0^0.
				{
					gradX[i] += this.getCoefs()[i][j];
				}
				/*
				else if (j != this.getDegree())
				{
					gradX[i] = this.getCoefs()[i][j] * (this.getDegree() - j) * Math.pow(x[i], this.getDegree()-j-1);
				}
				*/
				else 
				{
					gradX[i] += this.getCoefs()[i][j] * (this.getDegree() - j) * Math.pow(x[i], this.getDegree()-j-1);	
				}
			}
		}
		return gradX;
	}
	
	public double gradientNorm (double[] x)				// returns the size of the gradient array
	{	
		double sum = 0; 
		double[] grad = this.gradient(x);
		for (int m = 0; m < this.getN(); m++)
		{
			sum += Math.pow(grad[m], 2);
		}
		return Math.sqrt(sum); 
	}
	

} // Polyn class defn
