package player;
import java.util.ArrayList;

import item.Item;
import field.Field;
import icefield.Controller;
/** 
 * Szereplő osztálynak a megvalósítása 
 */
public class Player extends Moveable implements java.io.Serializable{
	/**
	 * játékos aktuális életpontjai
 	 */
	protected int health;
	/**
	 * játékos maximális életpontjai
 	 */
	protected int maxHealth;
	/**
	 * játékos energiapontjai
 	 */
	protected int energy;
	/**
	 * a játékoson van-e búvárruha vagy sem
 	 */
	protected boolean wears_suit;
	/**
	 * eszköztár
 	 */
	protected ArrayList<Item> items;
	/**
	 * controller
 	 */
	protected Controller controller;
	/**
	 * szereplőtípus neve
 	 */
	protected String name;
	
	/** 
	 * A játékos konstruktora
	 * @param c a játék kontrollere
	 * @param startField a játékos kezdeti mezője
	 */
	public Player(Controller c, Field startField) {
		this.energy = 4;
		this.wears_suit = false;
		this.items = new ArrayList<Item>(5);
		this.controller = c;
		this.currentField = startField;
		currentField.Accept(this); //rárakja magát a paraméterben megadott Fieldre
	}

	/** 
	 * Üres konstruktor serializáláshoz
	 */
	public Player(){

	}
	
	/** 
	 * A játékos felvesz egy eszközt az eszköztárába arról a jégtábláról amin éppen áll.
	 */
	public void PickItemUp() {
		if (this.energy > 0) {
			if (currentField.getSnow() == 0){
				//kivesszük a currentField "inventoryjából" a legfelsőbb elemet
				Item item = currentField.PopItem();
				if (item != null){
					this.AddItem(item); //berakja az eszköztárba
					this.decrementEnergy(); //1 munkába került, ezért csökkenti az energiapontjait
					System.out.println("You picked up a " + item.getName()); //kiírja, hogy milyen tárgyat vett fel
				}
			}
			else{
				System.out.println("Field is covered with snow!");
			}
		}else
			System.out.println("Not enough energy");
		
		
	}
	
	/** 
	 * A játékos lerak egy eszközt az eszköztárából a currentFieldre.
	 * @param input bemenet a console-ról
	 * @return int a keresett item indexe
	 */
	public int SearchItem(String input) {
		String[] temp = input.split(" "); //felbontsa 2 stringre, space mentén
		String item = temp[1].toString(); //ez változóba bele rakja az Item nevét
		boolean found = false;
		for (int i = 0; i < items.size() && found == false ; i++) { //addig megy az Player items tömbjében ameddig nem talál
															//azonos nevű Itemet, vagy ameddig a végére nem ér
			if (items.get(i).getName().equals(item)) {	//ha talál akkor a found változót átállítja true-ra
				found = true;
				return i;
			}
		}
		return -1; //ha nem találta meg akkor -1-et térít vissza
	}

	/** 
	 * A játékos lép egyet a paraméternek megadott dir irányban lévő szomszédos mezőre. 
	 * @param dir szomszédos Field indexe, amerre lép 
	 */
	//
	public void Move(int dir) {
		//akkor próbál lépni, ha van energiája
		if (this.energy > 0)
		{
			try{
				if (getCurrentField().getIsUpsideDown()) { // ha lyukban van a játékos, akkor nem tud mozogni
					System.out.println("Player is in hole, can't move!");
				}else{
					currentField.Pass(dir, this); //meghívja a currentField Pass függvényét, 
					//kivételt dob ha nem tud a dir indexű szomszédra lépni
					//Ha a Field-en, amire lépett PolarBear van, akkor meghívja a Finish-t
					if (currentField.getPolarBear() != null){
						System.out.println("You became polarbear's dinner!");
						//controller.setLose(true);
						controller.Finish();
					}
				}
			}catch(Exception e){ //elkapjuk a kivételt
				System.out.println("No such neighbor!");
			}
		}else
			System.out.println("Not enough energy");
		
	}
	
	
	/** 
	 * Eltávolít egy paraméterben megadott Item-et a items-ből
	 * @param item eltávolítandó Item
	 */
	public void RemoveItem(Item item){
		items.remove(item);
	}

	
	/** 
	 * Használni próbál egy Item-et a Player
	 * @param item használni kívánt Item
	 */
	public void UseItem(Item item) {
		//akkor próbálja meg használni, ha van elég energia
		if (this.energy > 0)
		{
			item.Use(this);	
		}else{
			System.out.println("Not enough energy");
		}
		
	}

	
	/** 
	 * Megvizsgálja hány rakétaalkatrész van a Playernél
	 * @return int visszatér azzal a számmal, ahány rakétaalkatrész van a Playernél
	 */
	public int RocketParts(){
		int parts = 0;
		for (int i = 0; i < items.size(); i++){
			if (items.get(i).getName().equals("flare") || items.get(i).getName().equals("charge")
			 || items.get(i).getName().equals("gun") )
			 	++parts;
		}
		return parts;

	}

	
	/** 
	 * Megvizsgálja, hogy létezik-e olyan Item a Player items tárolójában, amit a paraméteren kap
	 * @param input standard inputról kap egy String-et
	 */
	public void UseItem(String input) { //paraméterben kap egy Stringet, amiben egy "use item" parancs van, ahol az item egy Item neve 
		int i = SearchItem(input);
		if (i == -1) {
			System.out.println("Player doesn't have this item"); //ha nem talált ilyen Item-et, akkor jelzi hogy nem talált
		}else {
			UseItem(items.get(i));
		}
	}
	
	
	/** 
	 * Berak egy Item-et a items tárolóba (eszköztár)
	 * @param item Item, amit be akarunk rakni az itemsbe
	 */
	public void AddItem(Item item){
		try {
			if (item != null){
				items.add(item); //az ArrayList add függvényével belerakja az inventory-ba az item-et					  
				//kivételt dob, ha nincs elég hely
				if (item.getName() == "divingsuit"){ //ha DivingSuit az Item, akkor Use-oljuk
					item.Use(this);
					System.out.println("Player now wears a divingsuit");
				}
			}
		}catch(Exception e) {	//elkapja a kivételt és kiír egy hibaüzenetet
			System.out.println("Player's inventory is full");
		}
		
	}

	
	/** 
	 * eltávolít egy egység havat arról a Field-ről, amin a Player áll
	 */
	public void RemoveSnow() { //eltávolít egy egység havat a currentFieldről
		//ha van elég energiánk
		if (energy > 0){
			if (currentField.DecrementSnow()) //DecrementSnow() visszatér true-val, ha sikeres
				decrementEnergy();
		}else{
			System.out.println("Not enough energy!");
		}
	}

	/** 
	 * Egy Player köre, amiben tevékenykedhet, az leszármazottak felül írják
	 */
	public void Turn(){

	}

	
	/** 
	 * wears_suit gettere
	 * @return boolean
	 */
	public boolean getWears_suit() {
		return wears_suit;
	}
	
	
	/** 
	 * wears_suit settere
	 * @param value boolean paraméter
	 */
	public void setWears_suit(boolean value) {
		wears_suit = value;
	}
	
	
	/** 
	 * health gettere
	 * @return int
	 */
	public int getHealth() {
		return health;
	}
	
	/** 
	 * health settere
	 * @param value int
	 */
	public void setHealth(int value) {
			this.health = value;
	}
	
	
	/** 
	 * Csökkenti a health értékét 1-el
	 */
	public void decrementHealth() {
		if (this.health > 0){
			this.health--;
			System.out.println("health: " + health);
		}
		if (health == 0) { //ha a health 0, akkor vége a játéknak
			//controller.setLose(true);
			controller.Finish(); 
		}
	}
	
	/** 
	 * Növeli a health értékét 1-el
	 */
	public void incrementHealth() {
		if (this.health < maxHealth){ //nem lehet nagyobb, mint a maxHealth
			this.health++;
			System.out.println("health: " + health);
		}else{
			System.out.println("Health is already maxed out!");
			System.out.println("health: " + health);
		}
	}
	
	
	/** 
	 * maxHealth gettere
	 * @return int
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/** 
	 * maxHealth settere
	 * @param value int típusú paraméter
	 */
	public void setMaxHealth(int value){
		maxHealth = value;
	}

	
	/** 
	 * energy gettere
	 * @return int
	 */
	public int getEnergy() {
		return energy;
	}
	
	
	/** 
	 * energy settere
	 * @param value int típusú paraméter
	 */
	public void setEnergy(int value) { //az energia nem lehet kisebb, mint 0 és nem lehet nagyobb, mint 4
			this.energy = value;
	}
	
	/** 
	 * Csökkenti az energy-t 1-el
	 */
	public void decrementEnergy(){
		this.energy--;
		System.out.println("energy level: " + energy);
	}
	
	
	/** 
	 * items gettere
	 * @return ArrayList<Item> az eszköztárral tér vissza
	 */
	public ArrayList<Item> getItems(){
		return items;
	}
	
	
	/**
	 * items settere 
	 * @param array ArrayList<Item> típusú paraméter
	 */
	public void setItems(ArrayList<Item> array){
		items = array;
	}

	
	/** 
	 * controller gettere
	 * @return Controller
	 */
	public Controller getController(){
		return controller;
	}
	
	
	/** 
	 * controller settere
	 * @param _controller Controller típusú paraméter
	 */
	public void setController(Controller _controller){
		controller = _controller;
	}

	/** 
	 * Kilistázza a Player eszköztárát
	 */
	public void ListItems() {
		if (items.size() != 0){
			for (int i = 0; i < items.size(); i++) {
				System.out.println(items.get(i).getName());
			}
		}else
			System.out.println("Inventory is empty!");
		
	}

	
	/** 
	 * name gettere
	 * @return String
	 */
	public String getName(){
		return name;
	}
	
	
	/** 
	 * name settere
	 * @param value
	 */
	public void setName(String value){
		name = value;
	}
	
}
