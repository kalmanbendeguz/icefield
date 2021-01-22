package item;

import player.Player;

/** 
 * A pisztoly, a raketa egyik alkotoresze
 */
public class Gun extends Item implements java.io.Serializable {
	
	/** 
	 * A pisztoly hasznalatanak megvalositasa. Jelen esetben ekkor nem tortenik semmi, mert a pisztolyt onmagaban nem lehet hasznalni.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		System.out.println("gun cannot be used individually");
	}

	/** 
	 * A pisztoly nevevel ter vissza(gun), azonositashoz kell
	 * @return gun (string)
	 */
	@Override
	public String getName() {
		return "gun";
	}
}
