package com.example.dungeonbuddy;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Store extends Activity {
	
	public List<Equip> storeInventory;
	public List<String> stringInventory;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store);

		storeInventory = getAllEquips();
		stringInventory = getAllEquipStrings();

		displayInventory();
	}
	
	public void close(View v)
	{
		this.finish();
	}
	
	
	private void displayInventory() {
		//TODO Programmatically Generate Equips on screen
		ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringInventory);
		ListView storeView = (ListView) findViewById(R.id.storeView);
		storeView.setAdapter(adapter);
	}
	
	private List<Equip> getAllEquips() {
		List<Equip> inv = new ArrayList<Equip>();
		
		//TODO MySQLite implementation
		
		//Hard coded for now;
		
		Equip eq = new Equip("Fuzzy Mittens of Unbridled Fury", 10, 2, 0, 300);
		inv.add(eq);
		eq = new Equip("Hawaian Shirt of Lazy Fatman", 3, 8, 1, 500);
		inv.add(eq);
		eq = new Equip("Neckbeard of Everlasting Virginity", 0, 10, 2, 600);
		inv.add(eq);
		eq = new Equip("Icosahedron of Critical Tumbling", 20, 20, 20, 2000);
		inv.add(eq);
		eq = new Equip("Codpiece of Epic Buttstuff", 10, 10, 12, 1000);
		inv.add(eq);
		
		return inv;
	}
	
	private List<String> getAllEquipStrings() {
		List<String> inv = new ArrayList<String>();
		
		inv.add("Fuzzy Mittens of Unbridled Fury\n" +
				"Might: 10  Magic:  2  Marksman:  0\n" +
				"500G");
		inv.add("Hawaian Shirt of Lazy Fatman\n" +
				"Might:  3  Magic:  8  Marksman:  1\n" +
				"500G");
		inv.add("Neckbeard of Everlasting Virginity\n" +
				"Might:  0  Magic: 10  Marksman:  2\n" +
				"600G");
		inv.add("Codpiece of Epic Buttstuff\n" +
				"Might: 10  Magic: 10  Marksman: 12\n" +
				"1000G");
		inv.add("Gauntlets of Dolphin Flogging\n" +
				"Might: 10  Magic: 4  Marksman: 2\n" +
				"800G");


		
		return inv;
	}

}

