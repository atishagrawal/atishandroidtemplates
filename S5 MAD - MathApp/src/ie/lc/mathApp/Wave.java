


package ie.lc.mathApp;





public enum Wave
{
	sine,
	triangle,
	square;
	
	
	
	
	
	public static Wave random() {
		Wave[] vals = Wave.values();	
		return vals[ Util.randomIntRange(0, vals.length) ];
	}
	
	
	
	
	
	public String toString() {
		return Util.capitalise( name() );
	}
	
	
	
	
	
	public double evaluate( double input, double wavelength, double low, double high ) {
		switch (this) {
			case sine:     return Geo.sineSync( input, wavelength, low, high );
			case triangle: return Geo.triSync ( input, wavelength, low, high );
			case square:   return Geo.sqrSync ( input, wavelength, low, high );
			default:       return Double.NaN;
		}
	}
}
