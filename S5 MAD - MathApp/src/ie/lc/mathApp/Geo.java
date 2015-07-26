


package ie.lc.mathApp;





/**
 * Provides advanced geometric and mathematical utility functions.
 */
public class Geo
{
	/**
	 * Linear interpolate from A to B by fraction F.
	 */
	public static double lerp( double a, double b, double f ) {
		return a + (b-a) * f;
	}
	
	
	
	
	
	/**
	 * Transform a linear [0:1] interpolant into a hermite curve.
	 */
	public static double hermite( double f ) {
		return f * f * (3.0 - (2.0 * f));
	}
	
	
	
	
	
	/**
	 * Hermite interpolate from A to B by fraction F.
	 */
	public static double herp( double a, double b, double f ) {
		return lerp( a, b, hermite(f) );
	}
	
	
	
	
	
	/**
	 * Normalise given current value and min/max.
	 */
	public static double unlerp( double v, double minv, double maxv ) {
		double base  = v    - minv;
		double delta = maxv - minv;
		return base / delta;
	}
	
	
	
	
	
	/**
	 * Clamp to inclusive range. 
	 */
	public static double clamp( double v, double minv, double maxv  ) {
		return Math.min( Math.max(v,minv), maxv );
	}
	
	
	
	
	
	/**
	 * Same as unlerp() but clamps result to [0:1] range.
	 * Linear version of the well-known smoothstep function.
	 */
	public static double boxStep( double v, double minv, double maxv ) {
		double ul = unlerp( v, minv, maxv );
		return clamp( ul, 0, 1 );
	}
	
	
	
	
	
	/**
	 * Transform a monotonically increasing linear input into a sinewave.
	 * Waveform: One dip and rise per period, zero at edges and centre.
	 * Cycle:    Dip -> zero -> rise -> zero  [v^]
	 * Range:    [-1:+1] inclusive.
	 */
	public static double sineSync( double input, double wavelength ) {
	    double half = wavelength * 0.5;
	    double mod  = (input % wavelength) - half;
	    double pm1  = mod / half;
	    return Math.sin( pm1 * Math.PI );
	}
	
	
	
	
	
	/**
	 * Same as sineSync but with user-defined output range.
	 */
	public static double sineSync( double input, double wavelength, double low, double high ) {
	    double sine = sineSync( input, wavelength );
	    double f    = (sine * 0.5) + 0.5;
	    return lerp( low, high, f );
	}
	
	
	
	
	
	/**
	 * Transform a monotonically increasing linear input into a triangle wave.
	 * Waveform: One dip and rise per period, zero at edges and centre.
	 * Cycle:    Dip -> zero -> rise -> zero  [v^]
	 * Range:    [-1:+1] inclusive.
	 */
	public static double triSync( double input, double wavelength ) {
		double half = wavelength * 0.50;
	    double off  = wavelength * 0.25;
		double m    = ((input+off) % wavelength) - half;
		double a    = Math.abs( m / half );
	    return (a - 0.5) * 2.0;
	}
	
	
	
	
	
	/**
	 * Same as triSync but with user-defined output range.
	 */
	public static double triSync( double input, double wavelength, double low, double high ) {
		double tri       = triSync( input, wavelength );
		double rangeConv = (tri + 1.0) * 0.5;
	    return lerp( low, high, rangeConv );
	}
	
	
	
	
	
	/**
	 * Transform a monotonically increasing linear input into a square wave.
	 * Waveform: One dip and rise per period, zero at edges and centre.
	 * Cycle:    Dip -> zero -> rise -> zero  [v^]
	 * Range:    [-1:+1] inclusive.
	 */
	public static double sqrSync( double input, double wavelength ) {
		double tri = triSync( input, wavelength );
		
		if      (tri >= +0.5) return  1.0;
		else if (tri <= -0.5) return -1.0;
		else 				  return  0.0;			
	}
	
	
	
	
	
	/**
	 * Same as sqrSync but with user-defined output range.
	 */
	public static double sqrSync( double input, double wavelength, double low, double high ) {
		double sqr       = sqrSync( input, wavelength );
		double rangeConv = (sqr + 1.0) * 0.5;
	    return lerp( low, high, rangeConv );
	}
	
	
	
	
	
	/**
	 * Get the absolute difference between two numbers.
	 */
	public static double absDiff( double x, double y ) {
		return Math.abs( x - y );
	}
	
	
	
	
	
	/**
	 * Round to nearest integer using arithmetic rounding.
	 * Math.round() uses banker's rounding which gives undesired results sometimes.
	 */
	public static double roundArith( double x ) {
		return  (x >= 0.0)            ?
				Math.floor( 	  x + 0.5 ) :
				Math.floor( 1.0 + x - 0.5 ) ; 
	}
	
	
	
	
	
	/**
	 * Round to nearest integer if the difference it would cause is <= the given threshold.
	 */
	public static double roundOnThresh( double x, double thresh ) {
		double rounded = roundArith( x );
		double diff    = absDiff( x, rounded );
		
		if (diff <= thresh)
			 return rounded;
		else return x;
	}
}












































