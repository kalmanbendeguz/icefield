package item;

import player.Player;

/** 
 * Torekeny aso megvalositasa, ugyanazt tudja mint a lapat, de harom hasznalat utan eltorik, es megszunik letezni.
 */
public class Spade extends Shovel implements java.io.Serializable {

	/** 
	 * Ures konstruktor szerializalashoz
	 */
	public Spade() {}
	/**
 	 * Az aso eletereje, kezdetben 3
 	 */
	private int health = 3;
	
	/** 
	 * A torekeny aso hasznalatanak megvalositasa. A szereplo ketto havat eltakarit, vagy 1/0-at, ha a mezon csak annyi van. Ha harmadjara hasznaljuk, eltorik, errol visszajelzest is kapunk. Egy energiaba kerul.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		super.Use(player); //hasznalatkor ugyanaz tortenik mint a lapat hasznalatakor,
		health--; //de az aso eletereje csokken eggyel
		if(health == 0) { // es ha eleri a nullat
			System.out.println("Spade broke"); //akkor eltorik
			player.RemoveItem(this); //es eltunik az eszkoztarunkbol
		}
	}
	
	/** 
	 * Az aso nevevel ter vissza(spade), azonositashoz kell
	 * @return spade (string)
	 */
	@Override
	public String getName() {
		return "spade";
	}
	/**
	 * Health gettere
	 * @return value
	 */
	public int getHealth()
	{
		return health;
	}
}
