


package ie.lc.fallApp;





public class Physics
{
	private final static double secondsInHour    = 3600.0;
	private final static double typicalHumanMass = 80.0; // KG
	
	private static Planet activePlanet = Planet.earth;
	
	
	
	
	
	/**
	 * Set the planet to consider in calculations.  Earth is the default.
	 */
	public static void setActivePlanet( Planet p ) {
		activePlanet = p;
	}
	
	
	
	
	
	/**
	 * Get planet currently used for computations.
	 * @return Planet
	 */
	public static Planet getActivePlanet() {
		return activePlanet;
	}
	
	
	
	
	
	/**
	 * Compute equivalent fall height given velocity.
	 * @param velocity Velocity in metres per second.
	 * @return height in metres.
	 */
	public static double computeEquivalentFallHeight( double velocity ) {
	    double joules  = kineticEnergy( typicalHumanMass, velocity );
	    return equivalentHeight( joules, typicalHumanMass, activePlanet.getSurfaceGravity() );
	}
	
	
	
	
	
	/**
	 * Compute energy-equivalent velocity given height.
	 * @param height Height in metres.
	 * @return Velocity in metres per second.
	 */
	public static double computeEquivalentVelocity( double height ) {
	    double joules = potentialEnergy( height, typicalHumanMass, activePlanet.getSurfaceGravity() );
	    return equivalentVelocity( joules, typicalHumanMass );
	}
	
	
	
	
	
	public static double kilometresPerHourToMetresPerSec( double kph ) {
		return (kph * 1000.0) / secondsInHour; 
	}
	
	
	
	
	
	public static double metresPerSecToKilometresPerHour( double mps ) {
		return (mps / 1000.0) * secondsInHour; 
	}
	
	
	
	
	
	/**
	 * Compute kinetic energy given mass and velocity.
	 * @param mass Mass in kilograms.
	 * @param velocity Velocity in metres per second.
	 * @return Energy in joules.
	 */
	private static double kineticEnergy( double mass, double velocity ) {
	    return 0.5 * mass * velocity * velocity;
	}
	
	
	
	
	
	/**
	 * Compute equivalent height given energy, mass and gravity.
	 * @param energy Energy in joules.
	 * @param mass Mass in kilograms.
	 * @param gravity Acceleration due to gravity in metres per second.
	 * @return Height in metres.
	 */
	private static double equivalentHeight( double energy, double mass, double gravity ) {
	    return energy / (mass * gravity);
	}
	
	
	
	
	
	/**
	 * Compute potential energy given height, mass and gravity.
	 * @param height Height in metres.
	 * @param mass Mass in kilograms.
	 * @param gravity Acceleration due to gravity in metres per second.
	 * @return Energy in joules.
	 */
	private static double potentialEnergy( double height, double mass, double gravity ) {
	    return height * mass * gravity;
	}
	 
	
	
	
	
	/**
	 * Compute equivalent velocity given energy and mass.
	 * @param energy Energy in joules.
	 * @param mass Mass in kilograms.
	 * @return Velocity in metres per second.
	 */
	private static double equivalentVelocity( double energy, double mass ) {
	    return Math.sqrt( (2.0 * energy) / mass );
	}
}













