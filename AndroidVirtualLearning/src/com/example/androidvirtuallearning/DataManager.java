package com.example.androidvirtuallearning;

import android.net.Uri;

public class DataManager {

	private static DataManager mInstance;
	
	private final boolean isOnline = false;
	
	private Student student;
	
	private Subject currentSubject;
	
	private DLCType dlcType;
	
	private Uri currentDLCScheme;
	
	private DataManager()
	{
	       // Initialize all global game data here
	}
	
	public static DataManager getInstance()
	{
		if(mInstance == null)
        {
            mInstance = new DataManager();
        }
    	
        return mInstance;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getCurrentSubject() {
		return currentSubject;
	}

	public void setCurrentSubject(Subject currentSubject) {
		this.currentSubject = currentSubject;
	}

	public DLCType getDlcType() {
		return dlcType;
	}

	public void setDlcType(DLCType dlcType) {
		this.dlcType = dlcType;
	}

	public Uri getCurrentDLCScheme() {
		return currentDLCScheme;
	}

	public void setCurrentDLCScheme(Uri currentDLCScheme) {
		this.currentDLCScheme = currentDLCScheme;
	}

	
	
}
