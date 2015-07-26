
package com.xecute.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 */
public class ProjectTaskFragment extends ListFragment implements ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

    Context mContext;

    public TaskListAdapter taskListAdapter;

    public TextView header;
    LinearLayout mainListView;

    ProjectTaskFragmentListener mCallback;

    MainActivity mainActivity;
    Boolean filter;

    ViewStub stub;
    ImageView imageView;

    private ActionMode mActionMode;

    int itemPosition;
    View itemRow;

    public interface ProjectTaskFragmentListener {
        public void onProjectTaskSelected(ListView l, View v, int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mContext = getActivity();

        filter = false;

        mainActivity = (MainActivity)getActivity();

        mainListView = (LinearLayout) inflater.inflate(R.layout.fragment_main_list, container, false);
        stub = (ViewStub) mainListView.findViewById(android.R.id.empty);
        stub.setLayoutResource(R.layout.task_empty_stub);

        header = (TextView) mainListView.findViewById(R.id.header);
        header.setText(mainActivity.selectedProject.getString("projectName"));

        setHasOptionsMenu(true);
        taskListAdapter = new TaskListAdapter(mContext, mainActivity.selectedProject, "All");
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

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListLongClick", "Activated onItemLongClick");


                if (mActionMode != null) {
                    return false;
                }
                itemPosition = position;
                itemRow = view;
                mainActivity.selectedTask = taskListAdapter.getItem(position);
                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);

                return true;
            }
        });

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.delete_context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_delete:
                    removeRow(itemRow, itemPosition);
                    mode.finish(); // Action picked, so close the CAB
                    return true;

                case R.id.item_edit:
                    editTask();
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
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.projects_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                Log.i("MAIN", "New Task selected.");
                createNewTask();
                return true;

            case R.id.action_filter:
                Log.i("MAIN", "Filter Task Selected.");

                AlertDialog.Builder filterBuilder = new AlertDialog.Builder(mContext);
                filterBuilder.setTitle(R.string.action_filter)
                        .setItems(R.array.filters, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        taskListAdapter = new TaskListAdapter(mContext,mainActivity.selectedProject, "All");
                                        filter = false;
                                        updateList(taskListAdapter);
                                        break;
                                    case 1:
                                        taskListAdapter = new TaskListAdapter(mContext,mainActivity.selectedProject, "Active");
                                        filter = true;
                                        updateList(taskListAdapter);
                                        break;
                                    case 2:
                                        taskListAdapter = new TaskListAdapter(mContext,mainActivity.selectedProject, "Completed");
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
            mCallback = (ProjectTaskFragmentListener) activity;
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

        mCallback.onProjectTaskSelected(l, v, position);
    }

    public void removeRow(final View row, final int position) {
        Log.i("Project Remove", "Selected item at position: " + position);

        final int initialHeight = row.getHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                int newHeight = (int) (initialHeight * (1 - interpolatedTime));
                if (newHeight > 0) {
                    row.getLayoutParams().height = newHeight;
                    row.requestLayout();
                }
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                getListView().removeViewInLayout(row);
                row.getLayoutParams().height = initialHeight;
                row.requestLayout();

                ParseObject taskToDelete = taskListAdapter.getItem(position);

                ParseQuery<ParseObject> query = ParseQuery.getQuery("comments");
                query.whereEqualTo("relatedTask", taskToDelete);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> parseObjects, ParseException e) {
                        for (ParseObject object : parseObjects) {
                            object.deleteInBackground();
                        }
                    }
                });

                taskToDelete.deleteInBackground(new DeleteCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.i("MAIN", "Error Deleting Project: " + e.getMessage());

                        } else {
                            taskListAdapter.loadObjects();

                        }
                    }
                });

            }
        });
        animation.setDuration(300);
        row.startAnimation(animation);
    }

    void createNewTask() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CreateTaskDialogFragment newFragment = new CreateTaskDialogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();

    }

    void editTask() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        EditTaskDialogFragment newFragment = new EditTaskDialogFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();
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
