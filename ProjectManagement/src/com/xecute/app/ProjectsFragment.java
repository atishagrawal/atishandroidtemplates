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
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 */
public class ProjectsFragment extends ListFragment implements
		ParseQueryAdapter.OnQueryLoadListener {

	Context mContext;
	public ProjectListAdapter projectListAdapter;

	public TextView header;
	LinearLayout mainListView;

	ViewStub stub;
	ImageView imageView;

	ProjectsFragmentListener mCallback;

	private ActionMode mActionMode;

	int itemPosition;
	View itemRow;

	Boolean filter;

	public interface ProjectsFragmentListener {
		public void onProjectSelected(ListView l, View v, int position);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		filter = false;

		mContext = getActivity();

		mainListView = (LinearLayout) inflater.inflate(
				R.layout.fragment_main_list, container, false);

		stub = (ViewStub) mainListView.findViewById(android.R.id.empty);
		stub.setLayoutResource(R.layout.project_empty_stub);

		header = (TextView) mainListView.findViewById(R.id.header);
		header.setText(R.string.projects);

		setHasOptionsMenu(true);
		projectListAdapter = new ProjectListAdapter(mContext, "All");
		projectListAdapter.setAutoload(false);
		projectListAdapter.addOnQueryLoadListener(this);
		setListAdapter(projectListAdapter);
		projectListAdapter.loadObjects();

		return mainListView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		getListView().getEmptyView().setVisibility(ListView.GONE);
		getListView().setOnItemLongClickListener(
				new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						Log.i("ListLongClick", "Activated onItemLongClick");

						if (mActionMode != null) {
							return false;
						}
						itemPosition = position;
						itemRow = view;
						// Start the CAB using the ActionMode.Callback defined
						// above
						mActionMode = getActivity().startActionMode(
								mActionModeCallback);
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
			case R.id.item_delete:
				removeRow(itemRow, itemPosition);
				mode.finish(); // Action picked, so close the CAB
				return true;

			case R.id.item_edit:
				editProject();
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
			Log.i("MAIN", "New Project Selected.");
			if (projectListAdapter.getCount() == 50) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage(R.string.demo).setPositiveButton(
						R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// FIRE ZE MISSILES!
							}
						});

				builder.create().show();
			} else {
				createNewProject();
			}

			return true;

		case R.id.action_filter:
			Log.i("MAIN", "Filter Project Selected.");

			AlertDialog.Builder filterBuilder = new AlertDialog.Builder(
					mContext);
			filterBuilder.setTitle(R.string.action_filter).setItems(
					R.array.filters, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								projectListAdapter = new ProjectListAdapter(
										mContext, "All");
								filter = false;
								updateList(projectListAdapter);
								break;
							case 1:
								projectListAdapter = new ProjectListAdapter(
										mContext, "Active");
								filter = true;
								updateList(projectListAdapter);
								break;
							case 2:
								projectListAdapter = new ProjectListAdapter(
										mContext, "Completed");
								filter = true;
								updateList(projectListAdapter);
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
			mCallback = (ProjectsFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ProjectsFragmentListener");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		mCallback.onProjectSelected(l, v, position);
	}

	@Override
	public void onLoading() {

	}

	@Override
	public void onLoaded(List list, Exception e) {
		Log.i("onLoaded", "FIRED");
		updateHeader();

		try {
			if (list.size() == 0 && filter) {
				imageView = (ImageView) mainListView
						.findViewById(R.id.emptyProjects);
				imageView.setBackgroundResource(R.drawable.no_data);
				filter = false;
			} else {
				if (imageView != null && !filter) {
					imageView.setBackgroundResource(R.drawable.empty_projects);
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void createNewProject() {
		AlertDialog.Builder projectBuilder = new AlertDialog.Builder(mContext);
		projectBuilder.setTitle(R.string.action_new);
		// Get the layout inflater
		// LayoutInflater inflater = this.getLayoutInflater();
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.create_project, null);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
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

		// noinspection ConstantConditions
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final ParseObject[] projectColor = new ParseObject[1];
						final ParseObject newProject;

						EditText projectName = (EditText) view
								.findViewById(R.id.project_name);
						TextView errorMessage = (TextView) view
								.findViewById(R.id.project_name_error);
						if (projectName.getText().toString().isEmpty()) {
							Log.i("Save Project", "Name is empty!");
							errorMessage.setVisibility(View.VISIBLE);

						} else {
							newProject = new ParseObject("project");
							newProject.put("projectName", projectName.getText()
									.toString());
							newProject.put("status", "New");

							ParseUser user = ParseUser.getCurrentUser();
							newProject.put("createdBy", user);

							ParseACL groupACl = new ParseACL();
							groupACl.setReadAccess(ParseUser.getCurrentUser(),
									true);
							groupACl.setWriteAccess(ParseUser.getCurrentUser(),
									true);
							newProject.setACL(groupACl);

							ParseQuery<ParseObject> query = ParseQuery
									.getQuery("color");
							query.whereEqualTo("useStatus", false);
							query.getFirstInBackground(new GetCallback<ParseObject>() {
								@Override
								public void done(ParseObject parseObject,
										ParseException e) {
									newProject.put("color", parseObject);
									parseObject.put("useStatus", true);
									parseObject.saveInBackground();

									newProject
											.saveInBackground(new SaveCallback() {
												public void done(
														ParseException e) {
													if (e != null) {
														Log.i("MAIN",
																"Error saving Project: "
																		+ e.getMessage());
														try {
															newProject.delete();
														} catch (ParseException e1) {
															e1.printStackTrace();
														}
													} else {

														projectListAdapter
																.loadObjects();

													}
												}
											});
								}
							});
							dialog.dismiss();
						}
					}
				});

	}

	public void editProject() {
		AlertDialog.Builder projectBuilder = new AlertDialog.Builder(mContext);
		projectBuilder.setTitle(R.string.edit_project);

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View view = inflater.inflate(R.layout.create_project, null);

		final ParseObject projectToEdit = projectListAdapter
				.getItem(itemPosition);
		final EditText projectName = (EditText) view
				.findViewById(R.id.project_name);

		projectName.setText(projectToEdit.getString("projectName"));

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

		// noinspection ConstantConditions
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						TextView errorMessage = (TextView) view
								.findViewById(R.id.project_name_error);
						if (projectName.getText().toString().isEmpty()) {
							Log.i("Save Project", "Name is empty!");
							errorMessage.setVisibility(View.VISIBLE);

						} else if (!projectToEdit.getString("projectName")
								.equals(projectName.getText().toString())) {
							projectToEdit.put("projectName", projectName
									.getText().toString());

							projectToEdit.saveInBackground(new SaveCallback() {
								public void done(ParseException e) {
									if (e != null) {
										Log.i("MAIN", "Error saving Project: "
												+ e.getMessage());
										try {
											projectToEdit.delete();
										} catch (ParseException e1) {
											e1.printStackTrace();
										}
									} else {

										projectListAdapter.loadObjects();

									}
								}
							});
							dialog.dismiss();
						}
					}

				});
	}

	public void removeRow(final View row, final int position) {
		Log.i("Project Remove", "Selected item at position: " + position);

		final int initialHeight = row.getHeight();
		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
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

				ParseObject projectToDelete = projectListAdapter
						.getItem(position);
				projectToDelete.getParseObject("color").fetchInBackground(
						new GetCallback<ParseObject>() {
							@Override
							public void done(ParseObject parseObject,
									ParseException e) {
								parseObject.put("useStatus", false);
								parseObject.saveInBackground();
							}
						});

				ParseQuery<ParseObject> query = ParseQuery.getQuery("task");
				query.whereEqualTo("parentProject", projectToDelete);
				query.findInBackground(new FindCallback<ParseObject>() {
					@Override
					public void done(List<ParseObject> parseObjects,
							ParseException e) {
						if (parseObjects != null || parseObjects.size() != 0) {
							for (ParseObject object : parseObjects) {

								ParseQuery<ParseObject> query2 = ParseQuery
										.getQuery("comments");
								query2.whereEqualTo("relatedTask", object);
								query2.findInBackground(new FindCallback<ParseObject>() {
									@Override
									public void done(
											List<ParseObject> parseObjects,
											ParseException e) {
										for (ParseObject object2 : parseObjects) {
											object2.deleteInBackground();
										}
									}
								});

								object.deleteInBackground();
							}
						}
					}
				});

				projectToDelete.deleteInBackground(new DeleteCallback() {
					public void done(ParseException e) {
						if (e != null) {
							Log.i("MAIN",
									"Error Deleting Project: " + e.getMessage());

						} else {
							projectListAdapter.loadObjects();

						}
					}
				});

			}
		});
		animation.setDuration(300);
		row.startAnimation(animation);
	}

	void updateHeader() {
		if (projectListAdapter.isEmpty()) {
			header.getLayoutParams().height = 0;
			header.setVisibility(View.GONE);

		} else {
			header.getLayoutParams().height = 60;
			header.setVisibility(View.VISIBLE);
		}
	}

	void updateList(ProjectListAdapter adapter) {
		adapter.addOnQueryLoadListener(this);
		getListView().setAdapter(adapter);
		getListView().getEmptyView().setVisibility(ListView.GONE);
	}

	// Keeping around for reference of dp to pixel conversion
	public int getPixelsFromDPS(int dps) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		int pixels = (int) (dps * scale + 0.5f);
		return pixels;
	}

}
