package com.xecute.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.parse.ParseObject;

/**
 */
public class WidgetService extends RemoteViewsService {
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new WidgetRVFactory(this.getApplicationContext(), intent);
	}
}

class WidgetRVFactory implements RemoteViewsService.RemoteViewsFactory {
	private List<ParseObject> mWidgetItems;
	private Context mContext;
	private int mAppWidgetId;

	public WidgetRVFactory(Context context, Intent intent) {
		mContext = context;
		mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
	}

	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {

		Log.i("WidgetService", "onDataSetChanged FIRED!");
		mWidgetItems = MainActivity.taskItems;
	}

	@Override
	public void onDestroy() {
		if (mWidgetItems != null) {
			mWidgetItems.clear();
		}

	}

	@Override
	public int getCount() {
		if (mWidgetItems != null && mWidgetItems.size() != 0) {
			return mWidgetItems.size();
		}
		return 4;
	}

	@Override
	public RemoteViews getViewAt(int position) {

		RemoteViews rv = new RemoteViews(mContext.getPackageName(),
				R.layout.stack_item);
		if (mWidgetItems != null && mWidgetItems.size() != 0) {

			String taskName = mWidgetItems.get(position).getString("taskName");
			if (taskName.length() > 28) {
				rv.setTextViewText(R.id.stack_item_name,
						taskName.substring(0, 28) + "...");
			} else {
				rv.setTextViewText(R.id.stack_item_name, taskName);
			}

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date date = mWidgetItems.get(position).getDate("dueDate");
			if (date != null) {
				String dateStr = df.format(date);
				rv.setTextViewText(R.id.stack_due_date, "DUE: " + dateStr);
			}

			String taskId = mWidgetItems.get(position).getObjectId();
			Bundle extras = new Bundle();
			extras.putString(WidgetProvider.EXTRA_ITEM, taskId);
			Intent fillInIntent = new Intent();
			fillInIntent.putExtras(extras);
			rv.setOnClickFillInIntent(R.id.stack_item, fillInIntent);
		}

		// Return the remote views object.
		return rv;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}