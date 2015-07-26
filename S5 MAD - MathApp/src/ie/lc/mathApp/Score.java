


package ie.lc.mathApp;

import java.io.File;
import java.io.IOException;
import android.os.Environment;





public class Score
{
	private static		 ScoreData data;
	private static final String	   FILE = "/mathApp/score.blob";
	
	
	
	
	
	public static int getSuccesses() {
		return data.successes;
	}





	public static int getAttempts() {
		return data.attempts;
	}
	
	
	
	
	
	public static int getSuccessPercent() {
		if (data.attempts > 0)
			 return (int) Geo.roundArith( (data.successes / (double) data.attempts) * 100.0 );
		else return 0;
	}
	
	
	
	
	
	public static void reset() {
		data.attempts  = 0;
		data.successes = 0;
		save();
	}
	
	
	
	
	
	public static void onCorrectAnswer() {
		load();
		data.attempts ++;
		data.successes++;
		save();
	}
	
	
	
	
	
	public static void onWrongAnswer() {
		load();
		data.attempts++;
		save();
	}
	
	
	
	
	
	public static void load() {
		ioCommon( false );
	}
	
	
	
	
	
	public static void save() {
		ioCommon( true );
	}
	
	
	
	
	
	private static void ioCommon( boolean write ) {
		File    dir   = Environment.getExternalStorageDirectory();
		String  state = Environment.getExternalStorageState();
		boolean ready = Environment.MEDIA_MOUNTED.equals( state );
		String  file  = dir.getAbsolutePath() + FILE;
		
		if (ready)
		if (! write) {
			if (Storage.exists( file ))
				 data = Storage.read( file, ScoreData.class );
			else data = new ScoreData();
		}
		else {
			new File(file).getParentFile().mkdirs();
			Storage.write( file, data );
		}
	}
}
