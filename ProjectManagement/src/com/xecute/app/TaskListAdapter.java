package com.xecute.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 */
public class TaskListAdapter extends ParseQueryAdapter<ParseObject> {
	Context mContext;

	public TaskListAdapter(Context context, final ParseObject project,
			final String filter) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				ParseQuery query = new ParseQuery("task");
				if (project != null) {
					query.whereEqualTo("parentProject", project);
				}
				if (filter.equals("Active")) {
					query.whereLessThan("percentCompleted", 100);
				} else if (filter.equals("Completed")) {
					query.whereEqualTo("percentCompleted", 100);
				}
				// Log.i("QUERY", "parentProject = " + project.getObjectId());
				return query;
			}
		});
		mContext = context;
		Log.i("PARSEADAPTER", "context = " + mContext);
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent) {
		Log.i("getItemView", "fired");
		if (v == null) {
			v = View.inflate(mContext, R.layout.project_list_row, null);
			Log.i("PARSEADAPTER", "Layout Inflated");
		}
		super.getItemView(object, v, parent);

		final ParseImageView colorImage = (ParseImageView) v
				.findViewById(R.id.icon);

		try {
			object.getParseObject("color").fetchIfNeededInBackground(
					new GetCallback<ParseObject>() {
						public void done(ParseObject object, ParseException e) {
							ParseFile colorFile = object
									.getParseFile("colorImage");
							if (colorFile != null) {
								colorImage.setParseFile(colorFile);
								colorImage
										.loadInBackground(new GetDataCallback() {
											@Override
											public void done(byte[] data,
													ParseException e) {
												// nothing to do
											}
										});
							}
						}
					});

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		TextView projectName = (TextView) v.findViewById(R.id.text1);
		projectName.setText(object.getString("taskName"));
		Log.i("QUERY", "taskName = " + object.getString("taskName"));

		TextView dueDate = (TextView) v.findViewById(R.id.created_date);
		if (object.getDate("dueDate") != null) {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date date = object.getDate("dueDate");
			String dateStr = df.format(date);
			dueDate.setText("Due: " + dateStr);
			Log.i("QUERY", "dueDate = " + dateStr);
		}

		TextView status = (TextView) v.findViewById(R.id.project_status);
		String percent = Integer.toString(object.getInt("percentCompleted"));
		status.setText(percent + "%");
		Log.i("QUERY", "status = " + object.getInt("status"));

		return v;
	}
}