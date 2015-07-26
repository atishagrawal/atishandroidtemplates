


package ie.lc.mathApp;

import android.text.*;





public abstract class TextWatcherAdapter implements TextWatcher {
	public void onTextChanged    ( CharSequence s, int start, int before, int count ) {}
	public void beforeTextChanged( CharSequence s, int start, int count,  int after ) {}
	public void afterTextChanged ( Editable s ) 									  {}
}
