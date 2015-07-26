package com.example.nsa;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.techage.databaseexample.DBUtils.AgentCRUD;
import com.techage.databaseexample.DBUtils.DatabaseContract.Agent;

public class SearchAgent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_agent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_agent, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		AgentCRUD crudAgent = new AgentCRUD(SearchAgent.this);

		Cursor cursorAgent = null;
		cursorAgent = crudAgent.getAgent();

		if (cursorAgent != null && cursorAgent.getCount() > 0) {

			cursorAgent.moveToFirst();
			do {

				((TextView) findViewById(R.id.txtSearchAgentName))
						.setText(cursorAgent.getString(cursorAgent
								.getColumnIndex(Agent.COLUMN_NAME_AGENT_NAME)));
				((TextView) findViewById(R.id.txtSearchAgentCodeName))
						.setText(cursorAgent.getString(cursorAgent
								.getColumnIndex(Agent.COLUMN_NAME_AGENT_CODE_NAME)));

			} while (cursorAgent.moveToNext());

		}
	}
}
