package com.xecute.app;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

/**
 */
public class MyTasksFragment extends ListFragment implements
		ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

	Context mContext;

	public TaskListAdapter taskListAdapter;

	public TextView header;
	LinearLayout mainListView;

	MyTaskFragmentListener mCallback;

	MainActivity mainActivity;
	Boolean filter;

	ViewStub stub;
	ImageView imageView;

	private ActionMode mActionMode;

	int itemPosition;
	View itemRow;

	public interface MyTaskFragmentListener {
		public void onMyTaskSelected(ListView l, View v, int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		mContext = getActivity();

		filter = false;

		mainActivity = (MainActivity) getActivity();

		mainListView = (LinearLayout) inflater.inflate(
				R.layout.fragment_main_list, container, false);
		stub = (ViewStub) mainListView.findViewById(android.R.id.empty);
		stub.setLayoutResource(R.layout.task_empty_stub);

		header = (TextView) mainListView.findViewById(R.id.header);
		header.setText(R.string.tasks);

		setHasOptionsMenu(true);
		taskListAdapter = new TaskListAdapter(mContext, null, "Mine");
		taskListAdapter.setAutoload(false);
		setListAdapter(taskListAdapter);
		taskListAdapter.addOnQueryLoadListener(this);
		taskListAdapter.loadObjects();

		return mainListView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		getListView().getEmptyView().setVisibility(ListView.GONE);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.my_tasks_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_filter:
			Log.i("MAIN", "Filter Task Selected.");

			AlertDialog.Builder filterBuilder = new AlertDialog.Builder(
					mContext);
			filterBuilder.setTitle(R.string.action_filter).setItems(
					R.array.filters, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								taskListAdapter = new TaskListAdapter(mContext,
										null, "All");
								filter = false;
								updateList(taskListAdapter);
								break;
							case 1:
								taskListAdapter = new TaskListAdapter(mContext,
										null, "Active");
								filter = true;
								updateList(taskListAdapter);
								break;
							case 2:
								taskListAdapter = new TaskListAdapter(mContext,
										null, "Completed");
								filter = true;
								updateList(taskListAdapter);
								break;
							}
						}

					});

			filterBuilder.create().show();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (MyTaskFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ProjectTaskFragmentListener");
		}
	}

	@Override
	public void onLoading() {

	}

	@Override
	public void onLoaded(List list, Exception e) {
		Log.i("onLoaded", "FIRED");
		updateHeader();
		if (list.size() == 0 && filter) {
			imageView = (ImageView) mainListView.findViewById(R.id.emptyTasks);
			imageView.setBackgroundResource(R.drawable.no_data);

			filter = false;
		} else {
			if (imageView != null && !filter) {
				imageView.setBackgroundResource(R.drawable.empty_tasks);
			}
			filter = false;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		mCallback.onMyTaskSelected(l, v, position);
	}

	void updateList(TaskListAdapter adapter) {
		adapter.addOnQueryLoadListener(this);
		getListView().setAdapter(adapter);
		getListView().getEmptyView().setVisibility(ListView.GONE);
	}

	void updateHeader() {
		if (taskListAdapter.isEmpty()) {
			header.getLayoutParams().height = 0;
			header.setVisibility(View.GONE);

		} else {
			header.getLayoutParams().height = 60;
			header.setVisibility(View.VISIBLE);
		}
	}
}
