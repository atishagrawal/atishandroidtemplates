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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class CommentListAdapter extends ParseQueryAdapter<ParseObject> {
	Context mContext;

	public CommentListAdapter(Context context, final ParseObject task) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery<ParseObject> create() {
				ParseQuery query = new ParseQuery("comments");
				query.whereEqualTo("relatedTask", task);
				query.orderByAscending("createdAt");
				return query;
			}
		});
		mContext = context;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent) {
		Log.i("getItemView", "fired");
		if (v == null) {
			v = View.inflate(mContext, R.layout.comment_list_row, null);
			Log.i("PARSEADAPTER", "Layout Inflated");
		}
		super.getItemView(object, v, parent);

		TextView commentText = (TextView) v.findViewById(R.id.commentText);
		commentText.setText(object.getString("commentText"));
		Log.i("QUERY", "taskName = " + object.getString("commentText"));

		final TextView userName = (TextView) v.findViewById(R.id.userName);
		object.getParseObject("owner").fetchIfNeededInBackground(
				new GetCallback<ParseUser>() {
					public void done(ParseUser object, ParseException e) {
						if (object != null && e == null) {
							userName.setText(object.getString("username"));
							Log.i("QUERY",
									"Username = "
											+ object.getString("username"));
						}
					}
				});

		TextView commentDate = (TextView) v.findViewById(R.id.commentDate);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = object.getCreatedAt();
		String dateStr = df.format(date);
		commentDate.setText(dateStr);
		Log.i("QUERY", "commentDate = " + dateStr);

		return v;
	}
}