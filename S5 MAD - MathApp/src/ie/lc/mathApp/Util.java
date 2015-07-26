


package ie.lc.mathApp;





/**
 * Provides generic utility methods.
 */
public class Util
{
	/**
	 * Sleep the calling thread.
	 * @param millis Time to sleep in milliseconds.
	 */
	public static void sleep( long millis ) {
		try {
			Thread.sleep( millis );
		}
		catch (InterruptedException ex) {
			Thread.interrupted();
		}
	}
	
	
	
	
	
	/**
	 * Get a random integer in the given half-open range.
	 * @param low
	 * @param high
	 * @return int
	 */
	public static int randomIntRange( int low, int high ) {
		return low + ((int) Math.floor(Math.random() * (high-low)));
	}
	
	
	
	
	
	/**
	 * Capitalise a word.
	 */
	public static String capitalise( String word ) {
		return word.substring( 0, 1 ).toUpperCase() + word.substring( 1 );
	}
}
