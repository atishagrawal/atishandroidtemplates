


package ie.lc.mathApp;

import java.io.Serializable;





public class ScoreData implements Serializable {
	public int attempts;
	public int successes;
	
	
	
	
	
	public ScoreData() {
		// Do nothing
	}
	
	
	
	
	
	public ScoreData( int attempts, int successes ) {
		this.attempts  = attempts;
		this.successes = successes;
	}
}
