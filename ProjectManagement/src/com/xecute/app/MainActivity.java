
package com.xecute.app;

import android.app.ActionBar;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener,
        ProjectsFragment.ProjectsFragmentListener, ProjectTaskFragment.ProjectTaskFragmentListener,
        CreateTaskDialogFragment.CreateTaskDialogListener, MyTasksFragment.MyTaskFragmentListener,
        EditTaskDialogFragment.EditTaskDialogListener{

    Context mContext;

    ActionBar actionBar;
    Spinner navSpinner;

    String listHeaderValue;

    public ParseObject selectedProject;
    public ParseObject selectedTask;
    String selectedColorStr;

    ProjectsFragment projectsFragment;
    ProjectTaskFragment projectTaskFragment;
    TaskDetailFragment taskDetailFragment;
    MyTasksFragment myTasksFragment;

    public static List<ParseObject> taskItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        navSpinner = new Spinner(mContext);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(mContext, R.array.action_list,
                android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);

        taskItems = new ArrayList<ParseObject>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("task");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                taskItems = parseObjects;
                if (e == null) {

                    taskItems = parseObjects;
                    // Prompts the WidgetProvider to update the widget data set
                    AppWidgetManager awm = AppWidgetManager.getInstance(mContext);
                    awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(new ComponentName(mContext,
                            WidgetProvider.class)), R.id.stack_view);
                }
            }
        });



        Bundle incomingData = getIntent().getExtras();
        if (incomingData != null) {
            if (incomingData.getBoolean("widget")) {
                String taskId = getIntent().getStringExtra(WidgetProvider.EXTRA_ITEM);
                Log.i("Widget TaskId", "taskId= " + taskId);
                widgetTaskSelected(taskId);
            }
        } else {
            FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction fragTrans = fragManager.beginTransaction();
            fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);

            projectsFragment = new ProjectsFragment();
            fragTrans.add(R.id.main_container, projectsFragment).commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                Log.i("MAIN", "Log Out Selected.");
                ParseUser.logOut();
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                // Removes activity from the stack so we can not navigate back
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {

            case 1:
                FragmentManager fragManager = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragManager.beginTransaction();
                fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);
                fragManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                projectsFragment = new ProjectsFragment();
                fragTrans.replace(R.id.main_container, projectsFragment).commit();


                break;

            case 2:
                Log.i("MAIN", "My Tasks Selected");
                FragmentManager fragManager2 = getSupportFragmentManager();
                FragmentTransaction fragTrans2 = fragManager2.beginTransaction();
                fragTrans2.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);
                fragManager2.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                myTasksFragment = new MyTasksFragment();
                fragTrans2.replace(R.id.main_container, myTasksFragment).commit();

                break;
        }

        return false;
    }


    @Override
    public void onProjectSelected(ListView l, View v, int position) {
        Log.i("MAIN_ACTIVITY", "Selected Project at position: " + position);
        selectedProject = (ParseObject) l.getItemAtPosition(position);
        String projectName = selectedProject.getString("projectName");
        selectedColorStr = selectedProject.getParseObject("color").getObjectId();
        Log.i("MAIN_ACTIVITY", "Project Name: " + projectName + " Project id: " + selectedProject.getObjectId());
        Log.i("MAIN_ACTIVITY", "Color: " + selectedColorStr);


        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);
        projectTaskFragment = new ProjectTaskFragment();

        fragTrans.replace(R.id.main_container, projectTaskFragment)
                .addToBackStack(null).commit();
    }


    @Override
    public void onProjectTaskSelected(ListView l, View v, int position) {
        Log.i("MAIN_ACTIVITY", "Selected Task at position: " + position);
        selectedTask = (ParseObject) l.getItemAtPosition(position);

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);
        taskDetailFragment = new TaskDetailFragment();

        fragTrans.replace(R.id.main_container, taskDetailFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onTaskCreate(String taskNameStr, Date date, ArrayList<ParseUser> users, String taskDescriptionStr) {

        final ParseObject newTask = new ParseObject("task");
        newTask.put("taskName", taskNameStr);
        newTask.put("taskDescription", taskDescriptionStr);
        ParseObject project = ParseObject.createWithoutData("project", selectedProject.getObjectId());
        newTask.put("parentProject", project);
        newTask.put("usersTasked", users);
        newTask.put("percentCompleted", 0);
        ParseObject color = ParseObject.createWithoutData("color", selectedColorStr);
        newTask.put("color", color);

        if (date != null) {
            Log.i("MAIN_TASK_CREATE", "DATA: " + taskNameStr + " - " + date.toString() + " - " + users.size() + " - " + taskDescriptionStr);
            newTask.put("dueDate", date);
        }
        Log.i("MAIN_TASK_CREATE", "DATA: " + selectedProject.getString("projectName"));

        ParseACL groupACl = new ParseACL();
        for (ParseUser user : users) {
            groupACl.setReadAccess(user, true);
            groupACl.setWriteAccess(user, true);
        }
        groupACl.setReadAccess(ParseUser.getCurrentUser(), true);
        groupACl.setWriteAccess(ParseUser.getCurrentUser(), true);
        newTask.setACL(groupACl);

        newTask.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    Log.i("MAIN", "Error saving Project: " + e.getMessage());
                    try {
                        newTask.delete();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                } else {

                    projectTaskFragment.taskListAdapter.loadObjects();

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("task");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> parseObjects, ParseException e) {
                            taskItems = parseObjects;
                            if (e == null) {

                                taskItems = parseObjects;
                                // Prompts the WidgetProvider to update the widget data set
                                AppWidgetManager awm = AppWidgetManager.getInstance(mContext);
                                awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(new ComponentName(mContext,
                                        WidgetProvider.class)), R.id.stack_view);
                            }
                        }
                    });

                }
            }
        });

        if (selectedProject.getString("status").equals("New")) {
            selectedProject.put("status", "Active");
            selectedProject.saveInBackground();
        }

    }

    @Override
    public void onMyTaskSelected(ListView l, View v, int position) {
        Log.i("MAIN_ACTIVITY", "Selected Task at position: " + position);
        selectedTask = (ParseObject) l.getItemAtPosition(position);

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();
        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);
        taskDetailFragment = new TaskDetailFragment();

        fragTrans.replace(R.id.main_container, taskDetailFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onTaskEdit(ParseObject editedTask) {
        editedTask.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                projectTaskFragment.taskListAdapter.loadObjects();
            }
        });
    }

    public void widgetTaskSelected(String taskId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("task");
        query.getInBackground(taskId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    selectedTask = object;
                    FragmentManager fragManager = getSupportFragmentManager();
                    FragmentTransaction fragTrans = fragManager.beginTransaction();
                    fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.back_enter, R.anim.back_exit);
                    taskDetailFragment = new TaskDetailFragment();

                    fragTrans.add(R.id.main_container, taskDetailFragment).commit();
                } else {
                    // something went wrong
                }
            }
        });

    }

}
