


package ie.lc.mathApp;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;





public abstract class SeekBarAdapter implements OnSeekBarChangeListener {
	public void onProgressChanged   ( SeekBar seekBar, int progress, boolean fromUser ) {}
	public void onStartTrackingTouch( SeekBar seekBar ) 								{}
	public void onStopTrackingTouch ( SeekBar seekBar ) 								{}
}


