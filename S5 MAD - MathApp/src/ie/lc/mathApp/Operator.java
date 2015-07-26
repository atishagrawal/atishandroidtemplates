


package ie.lc.mathApp;





public enum Operator
{
	plus,
	minus,
	multiply,
	divide;
	
	
	
	
	
	public String toString() {
		switch (this) {
			case plus:     return "+";
			case minus:    return "−";
			case multiply: return "×";
			case divide:   return "÷";
			default:       return "?";
		}
	}
	
	
	
	
	
	public static Operator random() {
		Operator[] vals = Operator.values();	
		return vals[ Util.randomIntRange(0, vals.length) ];
	}
	
	
	
	
	
	public double evaluate( double left, double right ) {
		switch (this) {
			case plus:     return left + right;
			case minus:    return left - right;
			case multiply: return left * right;
			case divide:   return left / right;
			default:       return Double.NaN;
		}
	}
}
