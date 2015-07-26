package ie.lc.mathApp;



import java.io.*;
import java.util.ArrayList;
import java.util.List;





/**
 * Provides IO utility functions.
 */
public class Storage
{
	/**
	 * Read a single object from a file.
	 * @param filename
	 * @param type
	 * @return Reference to object
	 */
	public static <T> T read( String filename, Class<T> type ) {
		T object = null;
		
		try {
			FileInputStream   fis = new FileInputStream( filename );
			ObjectInputStream ois = new ObjectInputStream( fis );
			
			object = (T) ois.readObject();
			ois.close();
		}
		catch (Exception ex) {
			throw new RuntimeException( ex );
		}
		
		return object;
	}
	
	
	
	
	
	/**
	 * Write an object to a file.
	 * @param filename
	 * @param object
	 */
	public static void write( String filename, Serializable object ) {
		try {
			FileOutputStream   fos = new FileOutputStream( filename );
			ObjectOutputStream oos = new ObjectOutputStream( fos );
			
			oos.writeObject( object );
			oos.close();
		}
		catch (Exception ex) {
			throw new RuntimeException( ex );
		}
	}
	
	
	
	
	
	/**
	 * Check whether a file exists.
	 * @param filename
	 * @return
	 */
	public static boolean exists( String filename ) {		
		return new File(filename).exists();
	}
}

































