package field;

import java.util.ArrayList;
import java.util.Stack;

import item.Item;
import player.Player;
import player.PolarBear;
import player.Moveable;

/**
 * Mezo osztaly
 * 
 *
 */
public class Field implements java.io.Serializable {
	private boolean isInspected = false;
	
	/**
	 *  Maximalis teherbiras
	 */
	private int maxWeight; 
	/**
	 * A mezon levo hoegysegek szama
	 */
	private int snow; 
	/**
	 * Erteke true ha a mezon epult sator, false ellenkezo esetben
	 */
	private boolean hasTent = false; 
	/**
	 * Erteke true ha a mezon epult iglu, false ellenkezo esetben
	 */
	private boolean hasIgloo = false;
	/**
	 *  Erteke true ha a mezo atfordult, false ellenkezo esetben
	 */
	private boolean isUpsideDown = false;
	/**
	 * A mezon talalhato eszkozok (pl aso) listaja
	 */
	private Stack<Item> items = new Stack<Item>(); 
	/**
	 *  A mezon levo szereplok listaja
	 */
	private ArrayList<Player> players = new ArrayList<Player>();
	/**
	 * A mezon allo jegesmedvere mutato pointer
	 */
	private PolarBear polarBear = null;
	/**
	 *  A mezo szomszedait tarolo lista
	 */
	private ArrayList<Field> neighbors = new ArrayList<Field>();
	
	/**
	 * Kontruktor
	 */
	public Field() {
	}

	/**
	 * Konstruktor
	 * @param maxWeight max teherbírás
	 * @param snow jelenlegi hó
	 * @param item tárgy a fielden
	 */
	public Field(int maxWeight, int snow, Item item) {
		this.SetIsInspected(false);
		this.maxWeight = maxWeight;
		this.snow = snow;
		if (item != null)
			items.push(item);
	}
	/**
	 * A maxweight gettere
	 * @return az meglévő maxweight értéke
	 */
	public int getMaxWeight() {
		return maxWeight;
	}
	/**
	 * A maxweight settere (stabil mező : maxweight  = 10)
	 * @param value az új érték
	 */
	public void setMaxWeight(int value) {
		maxWeight = value;
	}
	/**
	 * A snow gettere
	 * @return a snow meglévő értéke
	 */
	public int getSnow() {
		return snow;
	}
	/**
	 * A snow settere
	 * @param value a snow új értéke
	 */
	public void setSnow(int value) {
		snow = value;
	}
	/**
	 * A hastest settere
	 * @param value az új értéke
	 */
	public void setHasTent(boolean value) {
		if (!hasIgloo)
			hasTent = value;
	}
	/**
	 * A hastent gettere
	 * @return a meglévő értéke
	 */
	public boolean getHasTent() {
		return hasTent;
	}

	/**
	 * A hasigloo settere 
	 * @param value az új értéke
	 */
	public void setHasIgloo(boolean value) {
		if (maxWeight == 10 && !hasTent)
			hasIgloo = value;
		else
			System.out.println("Field is not stable");
	}
	/**
	 * A hasigloo gettere
	 * @return a meglévő értéke
	 */
	public boolean getHasIgloo() {
		return hasIgloo;
	}
	/**
	 * Az isupsidedown gettere 
	 * @return a meglévő értéke
	 */
	public boolean getIsUpsideDown() {
		return isUpsideDown;
	}
	/**
	 * Az isupsidedown settere
	 * @param value az új értéke
	 */
	public void setIsUpsideDown(boolean value) {
		isUpsideDown = value;
	}

	/**
	 * Az itemeket visszaadó getter
	 * @return a meglévő itemek
	 */
	public Stack<Item> getItems() {
		return items;
	}
	/**
	 * Az itemeket beállító setter
	 * @param value az itemek új vereme
	 */
	public void setItems(Stack<Item> value) {
		items = value;
	}

	/**
	 * A playerek tömbjének gettere
	 * @return a meglévő player tömb
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * A playerek tömbjének settere
	 * @param value a player tömb új értéke
	 */
	public void setPlayers(ArrayList<Player> value) {
		players = value;
	}
	/**
	 * Egy player hozzáadása a player tömbhöz
	 * @param p az új player
	 */
	public void AddPlayer(Player p) {
		players.add(p);
		p.setCurrentField(this);
	}
	/**
	 * A polarbear gettere
	 * @return a meglévő polarear
	 */
	public PolarBear getPolarBear() {
		return polarBear;
	}
	/**
	 * A polarbear settere
	 * @param pb az új érték
	 */
	public void setPolarBear(PolarBear pb) {
		polarBear = pb;
	}
	/**
	 * A szomszédok gettere
	 * @return a meglévő szomszédok
	 */
	public ArrayList<Field> getNeighbors() {
		return neighbors;
	}
	/**
	 * A szomszédok settere
	 * @param value az új érték
	 */
	public void setNeighbors(ArrayList<Field> value) {
		neighbors = value;
	}

	/**
	 *  Szomszed hozzaadasa
	 * @param neighbor az új szomszéd
	 */
	public void AddNeighbor(Field neighbor) {
		neighbors.add(neighbor);

	}

	/**
	 *  Visszaadja a paramerterkent megadott iranyban levo szomszed Field objektumot
	 * @param neighborIndex a szomszéd indexe
	 * @return a szomszéd
	 */
	public Field GetNeighbor(int neighborIndex) {
		return neighbors.get(neighborIndex);

	}

	/**
	 *  A parameterkent kapott directionIndex iranyba tovabb rakja a pb jegesmedvet
	 * @param directionIndex a megadott irány
	 * @param pb a jegesmedve
	 * @return ha létezik olyan field akkor igazzal tér vissza
	 */
	public boolean Pass(int directionIndex, PolarBear pb) {
		if (neighbors.get(directionIndex) != null) {
			polarBear = null;
			pb.setCurrentField(neighbors.get(directionIndex));
			neighbors.get(directionIndex).setPolarBear(pb);
			return true;
		}
		return false;
	}

	/**
	 *  A parameterkent kapott directionIndex iranyba tovabb rakja a player szereplot
	 * @param directionIndex a megadott irány
	 * @param player a játékos
	 * @return igazzal tér vissza ma mozoghat oda a játékos
	 */
	public boolean Pass(int directionIndex, Player player) {
		if (neighbors.get(directionIndex) != null && neighbors.get(directionIndex).Accept(player)) {
			this.Remove(player);
			player.setCurrentField(neighbors.get(directionIndex));
			if (neighbors.get(directionIndex).IsOverWeight()) {
				//isUpsideDown = true;
				neighbors.get(directionIndex).setIsUpsideDown(true);
				System.out.println("Player fell into hole!");
			}
			if (neighbors.get(directionIndex).getHasIgloo())
				System.out.println("Player stepped into igloo!");
			else if (neighbors.get(directionIndex).getHasTent())
				System.out.println("Player stepped into tent!");
			player.decrementEnergy();
			return true;
		}
		return false;
	}

	/**
	 *  Elhelyezi a szereplot a mezon
	 * @param player a játékos
	 * @return igazzal tér vissza ha sikeresen elhelyezte a játékost a mezőn
	 */
	public boolean Accept(Player player) {
		if (player != null) {
			players.add(player);
			return true;
		}
		return false;
	}

	/**
	 *  Eltavolitja a mezorol a parameterkent kapott szereplot
	 * @param player a mozgatható élőlény
	 */
	public void Remove(Moveable player) {
		players.remove(player);
	}

	/**
	 *  Hozzaad egy eszkozt a mezon levo eszkozokhoz
	 * @param item az eszköz
	 */
	public void PushItem(Item item) {
		items.push(item);
	}

	/**
	 *  Visszaadja az mezon levo eszkozok kozul az utolsot es torli
	 * @return a törölt elem
	 */
	public Item PopItem() {
		try {
			return items.pop(); // kiveszi a legfelsőbb elemet, ha üres kivételt dob
		} catch (Exception e) {
			System.out.println("No item on field");
			return null;
		}
	}

	/**
	 *  Visszaadja az mezon levo eszkozok kozul az utolsot
	 * @return a legutolsó eszköz
	 */
	public Item GetItem() {
		try {
			return items.peek();
		} catch (Exception e) {
			System.out.println("No item on field");
			return null;
		}

	}

	/**
	 *  Visszateresi erteke igaz, ha a mezon tul sok jatekos all (es ekkor atfordul),
	 *  false ellenkező esetben
	 * @return false ha nem nem érte el a teherbírásának maximumát a mező
	 */
	public boolean IsOverWeight() {
		if (players.size() > maxWeight) {
			this.isUpsideDown = true;
			return true;
		}
		return false;
	}

	/**
	 *  A mezon levo hoegysegek erteket 1-el csokkenti
	 * @return hamis ha már nincs hó a mezőn
	 */
	public boolean DecrementSnow() {
		if (snow != 0) {
			snow--;
			System.out.println("1 snow removed!");
			return true;
		} else
			System.out.println("Field already clear!");
		return false;
	}

	/**
	 *  A mezon levo hoegysegek erteket 1-el noveli
	 */
	public void IncrementSnow() {
		snow++;
	}

	/**
	 *  Mezon levo eszkozok kiirasa
	 */
	public void ListItems() {
		if (items.empty() == false)
			System.out.println(items);
	}

	public boolean GetIsInspected() {
		return isInspected;
	}

	public void SetIsInspected(boolean isInspected) {
		this.isInspected = isInspected;
	}
}