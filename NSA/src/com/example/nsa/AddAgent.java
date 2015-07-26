package com.example.nsa;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.techage.databaseexample.DBUtils.AgentCRUD;
import com.techage.databaseexample.DBUtils.DatabaseContract.Agent;

public class AddAgent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_agent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_agent, menu);
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

	public void buttonSubmit(View view) {
		// TODO Auto-generated method stub
		EditText edAgentName = (EditText) findViewById(R.id.edAddAgentName);
		String strAgentname = edAgentName.getText().toString();
		EditText edAgentCodeName = (EditText) findViewById(R.id.edAddAgentCodeName);
		String strAgentCodeName = edAgentCodeName.getText().toString();

		ContentValues contentValues = new ContentValues();
		contentValues.put(Agent.COLUMN_NAME_AGENT_NAME, strAgentname);
		contentValues.put(Agent.COLUMN_NAME_AGENT_CODE_NAME, strAgentCodeName);

		AgentCRUD agentCRUD = new AgentCRUD(AddAgent.this);
		agentCRUD.insertAgent(contentValues);
		startActivity(new Intent(AddAgent.this, LoggedIn.class));
		finish();
	}

}
