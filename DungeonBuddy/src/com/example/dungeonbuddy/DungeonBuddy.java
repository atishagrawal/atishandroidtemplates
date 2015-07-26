//NAME - DungeonBuddy!
//PURPOSE - Awesome virtual pet  for the android device!
//Creators! - Brad Walker, Michael Klein, Jack Kostecki, John Kydd and Craig Milhomens

package com.example.dungeonbuddy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;



public class DungeonBuddy implements Parcelable{
	//for now, we can have a constant, but we can change this to 
	//something more dynamic after we're done toying around with it
	private final static int DAYS_TIL_LEVEL = 7;
		
	private Date nextAge = null;
	private int DID = 0;
	private String DName = "";
	private String DClass = "";
	private int DTierC = 0;
	private int DMight = 0;
	private int DMagic = 0;
	private int DMarksman = 0;
	private int DWallet = 0;
	private Calendar c = Calendar.getInstance();
	private Date createdDate = c.getTime();
	
	private DBAdapter dbAdapter;
	
	String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(createdDate);
	//changed to be a bit more streamlined right now.
	//Warriors do not become wizards dammit.
	static String[] DClassList = {"Peasent","Fighter","Warrior","Warlord"};
	
	//evolution method. still a little confused on what I'm supposed to implement, so
	protected void evolveBuddy(){
		//update class
		upClass();
		setDClass(DTierC);
		
		//set the next occurrence of a class up? Can be tweaked.
		c.add(Calendar.DATE, DAYS_TIL_LEVEL);
		nextAge = c.getTime();
	}
	
	public Date getNextAge(){
		return nextAge;
	}
	
	public DungeonBuddy(Context ctx){
		//yeah, we need something not default for a constructor, 
		//but I'm not 100% on what we need to materialize at
		//the start. We do need to set it's age though!
		createdDate = c.getTime();
		c.add(Calendar.DATE, DAYS_TIL_LEVEL);
		nextAge = c.getTime();
		dbAdapter = new DBAdapter(ctx);
	}
	
	public DungeonBuddy(Parcel in)
    {
        readFromParcel(in);
    }
	
	//Getters
	public int getDID() {
		return DID;
	}
	public int getDMight(){
		return DMight;
	}
	public int getDMagic(){
		return DMagic;
	}
	public int getDMarksman(){
		return DMarksman;
	}
	public String getDName() {
		return DName;
	}
	public String getDClass() {
		return DName;
	}
	public int getDWallet() {
		return DWallet;
	}

	//setters
	public void setDID (int id) {
		this.DID = id;
	}
	public void setDName(String name){
		this.DName = name;
	}
	public void setDClass(int tierC){
		this.DClass = DClassList[tierC];
	}
	public void setDWallet(int dWallet) {
		DWallet = dWallet;
	}
	public String getCreatedDate(){
		return stringDate;
	}
	
	//level up methods.  These methods are used to increment the requested stat by one, 
	//so the upper classes don't need to know too much about the DungeonBuddy class
	//itself.
	private void upClass(){
		DTierC++;
	}
	
	protected void levelMight(){
		DMight++;
	}
	
	protected void levelMagic(){
		DMagic++;
	}
	
	protected void levelMarksman(){
		DMarksman++;
	}
	
	//Saves current buddy info to its respective save slot
	//Accepts: Nothing
	//Returns: Boolean indicating successful save
	public boolean saveBuddy(Context ctx, int slot) {
		
		return dbAdapter.updateBuddy(slot, DName, DClass, createdDate.toString(), 
				Integer.toString(DMight), Integer.toString(DMagic), Integer.toString(DMarksman),
				Integer.toString(DWallet), Integer.toString(DTierC));
		
	}
	
	//Loads this class with saved buddy info
	//Accepts: int 1-3 for which buddy to load from the buddy array
	//Returns: boolean indicating successful load
	public DungeonBuddy loadBuddy(Context ctx, int slot) {
		
		Cursor cursor = dbAdapter.getBuddy(slot);
		
		DID = cursor.getInt(0);
		DName = cursor.getString(1);
		DClass = cursor.getString(2);
		createdDate = c.getTime();
		DMight = cursor.getInt(4);
		DMagic = cursor.getInt(5);
		DMarksman = cursor.getInt(6);
		DWallet = cursor.getInt(7);
		DTierC = cursor.getInt(8);
		
		return this;
	}
	
	
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flg) {
		dest.writeString(this.DName);
		dest.writeString(this.DClass);
		dest.writeInt(this.DTierC);
		dest.writeInt(this.DMight);
		dest.writeInt(this.DMagic);
		dest.writeInt(this.DMarksman);
		//dest.wri
	}
	
	 public void readFromParcel(Parcel in)
	    {
	        // We just need to read back each
	        // field in the order that it was
	        // written to the parcel

		 this.DName = in.readString();
		 this.DClass = in.readString();
		 this.DTierC = in.readInt();
		 this.DMight = in.readInt();
		 this.DMagic = in.readInt();
		 this.DMarksman = in.readInt();
	    }
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	    {
	        public DungeonBuddy createFromParcel(Parcel in)
	        {
	            return new DungeonBuddy(in);
	        }

	        public Object[] newArray(int size)
	        {
	            return new DungeonBuddy[size];
	        }
	    };
}
