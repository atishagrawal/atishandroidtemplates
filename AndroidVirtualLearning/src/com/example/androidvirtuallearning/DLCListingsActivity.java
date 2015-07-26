package com.example.androidvirtuallearning;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DLCListingsActivity extends Activity implements OnItemClickListener {

	private ListView listOfDLC;
	
	private DataManager sharedData;

	private ArrayList<String> resourceNames;

	@SuppressWarnings("unused")
	private Uri dlc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dlclistings);
		
		sharedData = DataManager.getInstance();
		
		
		this.setTitle("" + sharedData.getDlcType().toString() + " Contents" );
		
		System.out.println("DLC Type on Listing View: " + sharedData.getDlcType().toString());
		
		
		if(sharedData.getDlcType() == DLCType.Text)
		{
			ComposeList(sharedData.getCurrentSubject().getTextLectures());
		}
		else if(sharedData.getDlcType() == DLCType.Video)
		{		
			ComposeList(sharedData.getCurrentSubject().getVideoPaths());
		}
		else if(sharedData.getDlcType() == DLCType.Image)
		{
			ComposeList(sharedData.getCurrentSubject().getImgLectures());	
		}
		else if(sharedData.getDlcType() == DLCType.PDF)
		{
			ComposeList(sharedData.getCurrentSubject().getPdfPaths());	
		}
		else if(sharedData.getDlcType() == DLCType.Quiz)
		{
			ComposeList(sharedData.getCurrentSubject().getQuizes());	
		}
		
		listOfDLC = (ListView)findViewById(R.id.listOfDLC);
		listOfDLC.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resourceNames));
		listOfDLC.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dlclistings, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		sharedData = DataManager.getInstance();
		
		dlc = null;
		
		Intent dlcViewing = new Intent(this, DLCViewingActivity.class);
		
		if(sharedData.getDlcType() == DLCType.Text)
		{
			//dlc = Uri.parse(getDLCPath(sharedData.getCurrentSubject().getTextLectures(), arg2));
			//intent.setDataAndType(dlc, "text/plain");
			
			sharedData.setCurrentDLCScheme(Uri.parse(getDLCPath(sharedData.getCurrentSubject().getTextLectures(), arg2)));
		}
		else if(sharedData.getDlcType() == DLCType.Video)
		{
			//dlc = Uri.parse(getDLCPath(sharedData.getCurrentSubject().getVideoPaths(), arg2));
			//intent.setDataAndType(dlc, "video/mpeg");
			
			sharedData.setCurrentDLCScheme(Uri.parse(getDLCPath(sharedData.getCurrentSubject().getVideoPaths(), arg2)));
		}
		else if(sharedData.getDlcType() == DLCType.Image)
		{
			//dlc = Uri.parse(getDLCPath(sharedData.getCurrentSubject().getImgLectures(), arg2));
			//intent.setDataAndType(dlc, "image/*");	
			sharedData.setCurrentDLCScheme(Uri.parse(getDLCPath(sharedData.getCurrentSubject().getImgLectures(), arg2)));
		}
		else if(sharedData.getDlcType() == DLCType.PDF)
		{
			//dlc = Uri.parse(getDLCPath(sharedData.getCurrentSubject().getPdfPaths(), arg2));
			//intent.setDataAndType(dlc, "application/pdf");
			sharedData.setCurrentDLCScheme(Uri.parse(getDLCPath(sharedData.getCurrentSubject().getPdfPaths(), arg2)));
		}
		else if(sharedData.getDlcType() == DLCType.Quiz)
		{
			//dlc = Uri.parse(getDLCPath(sharedData.getCurrentSubject().getQuizes(), arg2));
			//intent.setDataAndType(dlc, "text/plain");
			sharedData.setCurrentDLCScheme(Uri.parse(getDLCPath(sharedData.getCurrentSubject().getQuizes(), arg2)));
		}
        
        startActivity(dlcViewing);
	}
	
	private void ComposeList(ArrayList<Integer> arrayList)
	{
		
		resourceNames = new ArrayList<String>();
		
		if(arrayList != null)
		{
			for(int resID : arrayList)
			{
				System.out.println("Resourse Name: "+ getResources().getResourceName(resID));
				resourceNames.add(getResources().getResourceEntryName(resID).toString());
			}
		}
		
		
		if(resourceNames.size() == 0)
		{
			resourceNames.add("No Content Available");
		}
	}
	
	private String getDLCPath(ArrayList<Integer> arrayList, int indexToGrab)
	{
		String result  = "null";
		
		if(arrayList != null)
		{
			
			result = getResources().getResourceEntryName(arrayList.get(indexToGrab)).toString();
			
			if(sharedData.getDlcType() == DLCType.Text)
			{
				result += ".doc";
			}
			else if(sharedData.getDlcType() == DLCType.Video)
			{
				result += ".mp4";
			}
			else if(sharedData.getDlcType() == DLCType.Image)
			{
				result += ".png";
			}
			else if(sharedData.getDlcType() == DLCType.PDF)
			{
				result += ".pdf";
			}
			else if(sharedData.getDlcType() == DLCType.Quiz)
			{
				result += ".txt";
			}
			
			
		}
		
		return result;
	}

}
