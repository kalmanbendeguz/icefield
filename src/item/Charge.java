package item;

import player.Player;

/** 
 * A patron, a raketa egyik alkotoresze
 */
public class Charge extends Item implements java.io.Serializable {

	/** 
	 * A patron hasznalatanak megvalositasa. Jelen esetben ekkor nem tortenik semmi, mert a patront onmagaban nem lehet hasznalni.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		System.out.println("charge cannot be used individually");
	}
	
	/** 
	 * A patron nevevel ter vissza(charge), azonositashoz kell
	 * @return charge (string)
	 */
	@Override
	public String getName() {
		return "charge";
	}

}
