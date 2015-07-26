package com.xecute.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
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
public class ProjectListAdapter extends ParseQueryAdapter<ParseObject> {
	Context mContext;

	public ProjectListAdapter(Context context, final String filter) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				if (filter.equals("All")) {
					ParseQuery query = new ParseQuery("project");
					// query.whereEqualTo("createdBy",
					// ParseUser.getCurrentUser());
					Log.i("QUERY", "Query = " + query);
					return query;
				} else if (filter.equals("Active")) {
					ParseQuery query = new ParseQuery("project");
					query.whereEqualTo("status", "Active");
					return query;
				} else if (filter.equals("Completed")) {
					ParseQuery query = new ParseQuery("project");
					query.whereEqualTo("status", "Completed");
					return query;
				}

				return null;
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

		object.getParseObject("color").fetchIfNeededInBackground(
				new GetCallback<ParseObject>() {
					public void done(ParseObject object, ParseException e) {
						try {
							if (object != null) {
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

						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				});

		TextView projectName = (TextView) v.findViewById(R.id.text1);
		projectName.setText(object.getString("projectName"));
		Log.i("QUERY", "ProjectName = " + object.getString("projectName"));

		TextView projectDate = (TextView) v.findViewById(R.id.created_date);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = object.getCreatedAt();
		String dateStr = df.format(date);
		projectDate.setText(dateStr);
		Log.i("QUERY", "createdAt = " + dateStr);

		TextView projectStatus = (TextView) v.findViewById(R.id.project_status);
		if (object.getString("status").equals("Active")) {
			projectStatus.setTextColor(Color.parseColor("#009900"));
		}
		projectStatus.setText(object.getString("status"));
		Log.i("QUERY", "status = " + object.getString("status"));

		return v;
	}
}
