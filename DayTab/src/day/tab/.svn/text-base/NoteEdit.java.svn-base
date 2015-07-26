package day.tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
/**Used when editing notes.*/
public class NoteEdit extends Activity {

	/**Used to store the body of the note*/
	private EditText mBodyText;
	/**Used to store the row id of the note on the database*/
	private Long mRowId;
	/**Aids in the manipulation of notes between the database and the program */
	private NotesDbAdapter mDbHelper;
	
	/**Variable used to store the day and time to be used */
	private String day="day";
	private String time="time";

	
	/**Called when the activity is ended*/
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(NotesDbAdapter.KEY_ROWID, mRowId);
	}
	
	/**Called when an activity is going into the background*/
	protected void onPause() {
		super.onPause();
		saveState();
	}
	
	/**Called after onPause to allow the user to interact with the program again*/
	protected void onResume() {
		super.onResume();
	}
	
	/**Saves the current note*/
	private void saveState() {
		
		String body = mBodyText.getText().toString();

		if (mRowId == null) {
			long id = mDbHelper.createNote(time, body, day);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, time, body, day);
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.note_edit);
		setTitle(R.string.edit_note);

		// Create the two spinners for the note edit page
		Spinner spin = (Spinner) findViewById(R.id.spin);
	    ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(
	            this, R.array.hours, android.R.layout.simple_spinner_item);
	    adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spin.setAdapter(adap);
	    
	    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            Object item = parent.getItemAtPosition(pos);
	            time = item.toString();
	        }
	        public void onNothingSelected(AdapterView<?> parent) {
	        }
	    });
	    
	    // Create the text box for the body of the note
		mBodyText = (EditText) findViewById(R.id.body);
		
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.days, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    
	    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            Object item = parent.getItemAtPosition(pos);
	            day = item.toString();
	        }
	        public void onNothingSelected(AdapterView<?> parent) {
	        }
	    });


		Button confirmButton = (Button) findViewById(R.id.confirm);

		mRowId = (savedInstanceState == null) ? null :
			(Long) savedInstanceState.getSerializable(NotesDbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(NotesDbAdapter.KEY_ROWID):null;
		}
		
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}