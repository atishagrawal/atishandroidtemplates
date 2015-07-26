package day.tab;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**Contains information relating to each tab*/
public class daytab extends ListActivity{
	/**Used when creating a new note*/
	private static final int ACTIVITY_CREATE=0;
	/**Used when editing a note*/
	private static final int ACTIVITY_EDIT=1;


	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	/**Variables used for createNote() and fectchNote()**/
	private String daynew;
	private String time;

	/**Aids in the manipulation of notes between the database and the program*/
	private NotesDbAdapter mDbHelper;
	/**Called when the activity is created*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_list);

		// Get the day from the Intent sent by Timetable.java
		daynew = getIntent().getStringExtra("EXTRA_DAY");

		// Connect to the database and fill data
		mDbHelper = new NotesDbAdapter(this);
		mDbHelper.open();
		fillData();
		registerForContextMenu(getListView());


		String[] hour = getResources().getStringArray(R.array.hours);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, hour));
		final ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				time=lv.getItemAtPosition(position).toString();
				if(time.startsWith("0") || time.startsWith("1") || time.startsWith("2")){
					mDbHelper.open();
					item();
				}
				else{
					mDbHelper.open();
					onCreate(null);
				}
				registerForContextMenu(getListView());
			}
		});
	}
	/** Called when the activity is first created. */
	private void fillData() {
		// Get all of the rows from the database and create the item list
		Cursor notesCursor = mDbHelper.fetchAllNotes(daynew);
		startManagingCursor(notesCursor);

		// Create an array to specify the fields we want to display in the list (only TITLE)
		String[] from = new String[]{NotesDbAdapter.KEY_TITLE};

		// and an array of the fields we want to bind those fields to (in this case just text1)
		int[] to = new int[]{R.id.text1};

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = 
			new SimpleCursorAdapter(this, R.layout.notes_row, notesCursor, from, to);
		setListAdapter(notes);
	}

	/** Called when one note is to be displayed. */
	private void item() {
		// Get all of the rows from the database where daynew and time match
		Cursor notesCursor = mDbHelper.fetchNote(daynew, time);
		startManagingCursor(notesCursor);

		// Create an array to specify the fields we want to display in the list (only BODY)
		String[] from = new String[]{NotesDbAdapter.KEY_BODY};

		// and an array of the fields we want to bind those fields to (in this case just text1)
		int[] to = new int[]{R.id.text1};

		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = 
			new SimpleCursorAdapter(this, R.layout.notes_row, notesCursor, from, to);
		setListAdapter(notes);
	}

	/** Called when an options menu is first created
	 * 
	 * @param menu 	The options menu in which you place your items
	 * */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		return true;
	}

	/**Called when an item is selected
	 * 
	 * @param featureId 	The panel that the menu is in
	 * @param item 	The menu item that was selected 
	 * */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case INSERT_ID:
			createNote();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	/**Called when the context menu is being built
	 * 
	 * @param menu 	The context menu that is being built
	 * @param v 	The view for which the context menu is being built
	 * @parammenuInfo 	Extra information about the item for which the context menu should be shown. This information will vary depending on the class of v. 
	 * */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}

	/**Called when a context item is selected
	 * 
	 * @param item 	The context menu item that was selected
	 * */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			mDbHelper.deleteNote(info.id);
			item();
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	/**Called when a new note is created*/
	private void createNote() {
		Intent i = new Intent(this, NoteEdit.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}
	
	/**Called when a member of a list is selected
	 * 
	 * @param l 	The ListView where the click happened
	 * @param v 	The view that was clicked within the ListView
	 * @param position 	The position of the view in the list
	 * @param id 	The row id of the item that was clicked
	 * */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, NoteEdit.class);
		i.putExtra(NotesDbAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}
	
	/**Called when an activity you launched exits
	 *
	 * @param requestCode 	The integer request code originally supplied to startActivityForResult()
	 * @param resultCode 	The integer result code returned by the child activity
	 * @param intent 	Returns result data to the caller
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		item();
	}

	/** Causes the onCreate() method to be called when back button is pressed*/
	@Override
	public void onBackPressed(){
		onCreate(null);
		return;
	}
}
