package item;

import player.Player;

/** 
 * Eszkoz osztaly, ami egy jatekbeli eszkozt (pl. aso, kotel, etel) reprezental, ezeknek absztrakt ososztalya.
 */
public abstract class Item implements java.io.Serializable {
	
	/** 
	 * Az eszkoz hasznalatanak megvalositasat implementalo fuggvenyek ezt a fuggvenyt definialjak felul.
	 * @param player A szereplo, aki hasznalja
	 */
	public abstract void Use(Player player);
	
	/** 
	 * Az item nevevel ter vissza, azonositashoz kell
	 * @return megvalositas fuggo (string)
	 */
	public abstract String getName();
}
