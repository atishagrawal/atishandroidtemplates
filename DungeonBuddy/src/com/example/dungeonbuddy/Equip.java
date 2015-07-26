package com.example.dungeonbuddy;

public class Equip {
	
	//Stats
	int _id;
	String _name;
	int _might;
	int _magic;
	int _marks;
	int _price;
	boolean _purchased;
	boolean _equipped;
	
	//GettersandSetters
	public void set_id(int _id) {
		this._id = _id;
	}
	public int get_id() {
		return _id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public int get_might() {
		return _might;
	}
	public void set_might(int _might) {
		this._might = _might;
	}
	public int get_magic() {
		return _magic;
	}
	public void set_magic(int _magic) {
		this._magic = _magic;
	}
	public int get_marks() {
		return _marks;
	}
	public void set_marks(int _marks) {
		this._marks = _marks;
	}
	public int get_price() {
		return _price;
	}
	public void set_price(int _price) {
		this._price = _price;
	}
	public boolean is_purchased() {
		return _purchased;
	}
	public boolean is_equipped() {
		return _equipped;
	}
	public void set_purchased(boolean _purchased) {
		this._purchased = _purchased;
	}
	public void set_equipped(boolean _equipped) {
		this._equipped = _equipped;
	}

	//Default Constructor
	public Equip() {
		_name = "";
		_might = 0;
		_magic = 0;
		_marks = 0;
		_price = 0;
		_purchased = false;
		_equipped = false;
	}
	//Statless Constructor
	public Equip( String n) {
		_name = n;
		_might = 0;
		_magic = 0;
		_marks = 0;
		_price = 0;
		_purchased = false;
		_equipped = false;
	}
	//Priceless Constructor
	public Equip( String n, int mght, int mgc, int mrk) {
		_name = n;
		_might = mght;
		_magic = mgc;
		_marks = mrk;
		_price = 0;
		_purchased = false;
		_equipped = false;
	}
	//Complete Constructor
	public Equip( String n, int mght, int mgc, int mrk, int p) {
		_name = n;
		_might = mght;
		_magic = mgc;
		_marks = mrk;
		_price = p;
		_purchased = false;
		_equipped = false;
	}
	
	public void get_by_id(int id) {
		//TODO - SQLite crap
		
		
	}
	
}
