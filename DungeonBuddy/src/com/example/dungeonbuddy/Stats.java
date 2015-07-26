package com.example.dungeonbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Stats extends Activity {
	
	private DungeonBuddy statsHolder;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		statsHolder = new DungeonBuddy(this);
		
		setContentView(R.layout.stats);
		
		Bundle bn = getIntent().getExtras();
		
		statsHolder = bn.getParcelable("stats");
		
		TextView tn = (TextView)findViewById(R.id.textView1);
		tn.setText(statsHolder.getDName());
		
		TextView tda = (TextView)findViewById(R.id.textView5);
		tda.setText("Birthday: " + String.valueOf(statsHolder.getCreatedDate()));
		
		TextView tmi = (TextView)findViewById(R.id.textView2);
		tmi.setText("Might:" + String.valueOf(statsHolder.getDMight()));
		
		TextView tma = (TextView)findViewById(R.id.textView3);
		tma.setText("Magic:" + String.valueOf(statsHolder.getDMagic()));
		
		TextView tmr = (TextView)findViewById(R.id.textView4);
		tmr.setText("Marksmen:" + String.valueOf(statsHolder.getDMarksman()));
		
		}
	
	public void close(View v)
	{
		this.finish();
	}

}
