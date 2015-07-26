package com.xecute.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 */
public class EditTaskDialogFragment extends DialogFragment implements
		View.OnClickListener {

	Context mContext;

	EditTaskDialogListener mCallback;
	private ActionMode mActionMode;

	MainActivity mainActivity;

	ParseObject selectedTask;

	Button chooseDateBtn;
	Button taskUserBtn;
	TextView dateChosenTxt;
	EditText taskName;
	EditText taskDescription;
	ListView userList;

	DatePicker mDatePicker;

	int monthSelected, daySelected, yearSelected;
	Date taskDueDate;
	int initialMonth, initialDay, initialYear;
	Date initialDueDate;

	ArrayList<ParseUser> taskedUsers;
	UserListAdapter userListAdapter;

	public interface EditTaskDialogListener {
		public void onTaskEdit(ParseObject editedTask);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		LinearLayout createTaskView = (LinearLayout) inflater.inflate(
				R.layout.create_task, container, false);

		mContext = getActivity();

		mainActivity = (MainActivity) getActivity();

		selectedTask = mainActivity.selectedTask;

		chooseDateBtn = (Button) createTaskView
				.findViewById(R.id.chooseDateBtn);
		chooseDateBtn.setOnClickListener(this);
		taskUserBtn = (Button) createTaskView.findViewById(R.id.taskUsersBtn);
		taskUserBtn.setOnClickListener(this);

		dateChosenTxt = (TextView) createTaskView.findViewById(R.id.dateChosen);
		taskName = (EditText) createTaskView.findViewById(R.id.taskName);
		taskDescription = (EditText) createTaskView
				.findViewById(R.id.taskDescription);
		userList = (ListView) createTaskView.findViewById(R.id.userList);

		initialDueDate = selectedTask.getDate("dueDate");
		Calendar cal = Calendar.getInstance();
		cal.setTime(initialDueDate);
		initialYear = cal.get(Calendar.YEAR);
		initialMonth = cal.get(Calendar.MONTH);
		initialDay = cal.get(Calendar.DAY_OF_MONTH);
		dateChosenTxt.setText(initialMonth + 1 + "/" + initialDay + "/"
				+ initialYear);

		taskName.setText(selectedTask.getString("taskName"));
		taskDescription.setText(selectedTask.getString("taskDescription"));

		taskedUsers = new ArrayList<ParseUser>();

		JSONArray usersArray = selectedTask.getJSONArray("usersTasked");
		Log.i("EditTaskFrag", "user json array = " + usersArray.toString());
		for (int i = 0, j = usersArray.length(); i < j; i++) {
			try {
				JSONObject userObj = usersArray.getJSONObject(i);
				String userId = userObj.getString("objectId");
				Log.i("userId", "userId = " + userId);
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.getInBackground(userId, new GetCallback<ParseUser>() {
					@Override
					public void done(ParseUser parseUser, ParseException e) {
						if (e == null) {
							taskedUsers.add(parseUser);
							userListAdapter.notifyDataSetChanged();
						} else {
							Log.i("Get User", "error = " + e.getMessage());
						}

					}
				});
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {

			}
		}

		userListAdapter = new UserListAdapter(mContext, taskedUsers);
		userList.setAdapter(userListAdapter);

		return createTaskView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		// Start the CAB using the ActionMode.Callback defined above
		mActionMode = getActivity().startActionMode(mActionModeCallback);
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.task_context_menu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.item_save:
				saveTaskData();
				mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
			Log.i("CREATE_TASK", "Exited!");
			getActivity().getSupportFragmentManager().popBackStack();
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (EditTaskDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement EditTaskDialogListener");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chooseDateBtn:
			Log.i("CREATE_TASK", "Choose date btn");
			final Calendar c = Calendar.getInstance();

			final DatePickerDialog datePickerDialog;
			if (initialDay != 0 && initialMonth != 0 && initialYear != 0) {
				datePickerDialog = new DatePickerDialog(mContext, null,
						initialYear, initialMonth, initialDay) {
				};

			} else {
				int mYear = c.get(Calendar.YEAR);
				int mMonth = c.get(Calendar.MONTH);
				int mDay = c.get(Calendar.DAY_OF_MONTH);

				datePickerDialog = new DatePickerDialog(mContext, null, mYear,
						mMonth, mDay) {
				};
			}

			mDatePicker = datePickerDialog.getDatePicker();

			datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
					"Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (which == DialogInterface.BUTTON_NEGATIVE) {
								dialog.cancel();
							}
						}
					});

			datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Done",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (which == DialogInterface.BUTTON_POSITIVE) {
								setDate(mDatePicker);
								dialog.dismiss();
							}
						}
					});

			datePickerDialog.setCancelable(true);
			datePickerDialog.show();
			break;

		case R.id.taskUsersBtn:
			Log.i("CREATE_TASK", "Task User btn");
			taskUser();
			break;
		}
	}

	void taskUser() {
		AlertDialog.Builder projectBuilder = new AlertDialog.Builder(mContext);
		projectBuilder.setTitle(R.string.user_name_input);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.task_user, null);

		projectBuilder
				.setView(view)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		final AlertDialog dialog = projectBuilder.create();
		dialog.show();

		final TextView errorMessage = (TextView) view
				.findViewById(R.id.user_name_error);
		final EditText userName = (EditText) view.findViewById(R.id.user_name);
		userName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				errorMessage.setVisibility(View.INVISIBLE);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		// noinspection ConstantConditions
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final Boolean[] wantToCloseDialog = { false };

						if (userName.getText().toString().isEmpty()) {
							Log.i("Save Project", "Username is empty!");
							errorMessage.setText("Username is empty!");
							errorMessage.setVisibility(View.VISIBLE);

						} else {
							ParseQuery<ParseUser> query = ParseUser.getQuery();
							query.whereEqualTo("username", userName.getText()
									.toString());
							query.findInBackground(new FindCallback<ParseUser>() {
								public void done(List<ParseUser> objects,
										ParseException e) {
									if (e == null && objects.size() > 0) {
										boolean addResult = updateTaskedUsersList(objects
												.get(0));
										if (addResult) {
											InputMethodManager imm = (InputMethodManager) getActivity()
													.getSystemService(
															Context.INPUT_METHOD_SERVICE);
											imm.hideSoftInputFromWindow(
													userName.getWindowToken(),
													0);
											dialog.dismiss();

										} else {
											errorMessage
													.setText("User is already tasked!");
											userName.setText("");
											errorMessage
													.setVisibility(View.VISIBLE);
										}

									} else {
										errorMessage
												.setText("Username not found!");
										errorMessage
												.setVisibility(View.VISIBLE);
									}
								}
							});
						}

					}
				});
	}

	public void setDate(DatePicker datePicker) {
		monthSelected = datePicker.getMonth();
		daySelected = datePicker.getDayOfMonth();
		yearSelected = datePicker.getYear();

		dateChosenTxt.setText(monthSelected + 1 + "/" + daySelected + "/"
				+ yearSelected);
		Calendar calendar = Calendar.getInstance();
		calendar.set(yearSelected, monthSelected, daySelected);
		taskDueDate = calendar.getTime();
	}

	public void saveTaskData() {
		Date date;

		String taskNameStr = taskName.getText().toString();
		if (taskDueDate != null) {
			date = taskDueDate;
		} else {
			date = null;
		}
		String taskDescriptionStr = taskDescription.getText().toString();

		selectedTask.put("taskName", taskNameStr);
		selectedTask.put("dueDate", taskDueDate);
		selectedTask.put("taskDescription", taskDescriptionStr);
		selectedTask.put("usersTasked", this.taskedUsers);
		ParseACL groupACl = new ParseACL();
		for (ParseUser user : this.taskedUsers) {
			groupACl.setReadAccess(user, true);
			groupACl.setWriteAccess(user, true);
		}
		groupACl.setReadAccess(ParseUser.getCurrentUser(), true);
		groupACl.setWriteAccess(ParseUser.getCurrentUser(), true);
		selectedTask.setACL(groupACl);

		mCallback.onTaskEdit(selectedTask);
	}

	public Boolean updateTaskedUsersList(ParseUser user) {
		Log.i("PARSE_USER_ADD", user.getUsername());
		for (int i = 0, j = taskedUsers.size(); i < j; i++) {
			if (user.getUsername().equals(taskedUsers.get(i).getUsername())) {
				return false;
			}
		}
		taskedUsers.add(user);
		userListAdapter.notifyDataSetChanged();
		return true;
	}

}