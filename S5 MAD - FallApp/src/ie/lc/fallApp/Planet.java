


package ie.lc.fallApp;

import java.io.Serializable;





public class Planet implements Serializable
{
	public static final Planet mercury = new Planet( "Mercury", 2439700.0, 3.30220E+23 );
	public static final Planet venus   = new Planet( "Venus",   6051800.0, 4.86760E+24 );
	public static final Planet earth   = new Planet( "Earth",   6371000.0, 5.97219E+24 );
	public static final Planet mars    = new Planet( "Mars",    3396200.0, 6.41850E+23 );
	
	public static final Planet[] innerPlanets = {
		mercury,
		venus,
		earth,
		mars
	};
	
	
	
	
	
	public String name;
	public double radius;
	public double mass;
	
	
	
	
	
	public Planet( String name, double radius, double mass ) {
		this.name   = name;
		this.radius = radius;
		this.mass   = mass;
	}
	
	
	
	
	
	public boolean equals( Object obj ) {
		if (obj == null)
			return false;
		
		if (! (obj instanceof Planet))
			return false;
		
		Planet p = (Planet) obj;
		
		if ( ! name.equals( p.name )
		||  mass   != p.mass
		||  radius != p.radius)
			return false;
		
		return true;
	}
	
	
	
	
	
	/**
	 * Compute acceleration due to gravity on the surface of this planet.
	 * @return Acceleration in metres per second.
	 */
	public double getSurfaceGravity() {
		double G = 6.67384E-11; // Gravitational constant
		return (G * mass) / (radius * radius);
	}
	
	
	
	
	
	public String toString() {
		return name;
	}
}
