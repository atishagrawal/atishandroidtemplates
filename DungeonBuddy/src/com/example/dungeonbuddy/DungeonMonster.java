package com.example.dungeonbuddy;

import java.util.Calendar;
import java.util.Random;

public class DungeonMonster {
	
	private int DMight = 0;
	private int DMagic = 0;
	private int DMarksmen = 0;
	private String DMname = "Monster";
	
	static String[] DTypeList = {"Mystic","Beefy","Slender"};
	static String[] DRaceList = {"Kobold","Orc","Sheep"};
	
	int ranRoll = 0;
	int ranRollRace = 0;
	Random randomGenerator = new Random();
	
	public DungeonMonster(){
		
	}
	
	public void randoRoll(){
		ranRoll = randomGenerator.nextInt(3);
		ranRollRace = randomGenerator.nextInt(3);
		
		DMname = DTypeList[ranRoll] + " " + DRaceList[ranRollRace];
		
		switch (ranRoll){
		case 0:
			DMagic = (int) Math.random() * (6);
			DMight = (int) Math.random() * (3);
			DMarksmen = (int) Math.random() * (3);
		case 1:
			DMagic = (int) Math.random() * (3);
			DMight = (int) Math.random() * (6);
			DMarksmen = (int) Math.random() * (3);
		case 2:
			DMagic = (int) Math.random() * (3);
			DMight = (int) Math.random() * (3);
			DMarksmen = (int) Math.random() * (6);
		}
	}
	
	public String getDMname()
	{
		return DMname;
	}
	
	public int getRoll(){
		ranRoll = (int) Math.random() * (2);
		return ranRoll;
	}
	
	public int getDMMM(int i){
		switch (i){
			case 0:
				return DMight;
			case 1:
				return DMagic;
			case 2:
				return DMarksmen;
		}
		return 0;
	}
	
	public int getDMight(){
		return DMight;
	}
	
	public int getDMagic(){
		return DMagic;
	}
	
	public int getDMarksmen(){
		return DMarksmen;
	}
}
