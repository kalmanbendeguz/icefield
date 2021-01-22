package item;

import player.Player;

/** 
 * A jelzofeny, a raketa egyik alkotoresze
 */
public class Flare extends Item implements java.io.Serializable {

	/** 
	 * A jelzofeny hasznalatanak megvalositasa. Jelen esetben ekkor nem tortenik semmi, mert a jelzofenyt onmagaban nem lehet hasznalni.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		System.out.println("flare cannot be used individually");
	}

	/** 
	 * A jelzofeny nevevel ter vissza(flare), azonositashoz kell
	 * @return flare (string)
	 */
	@Override
	public String getName() {
		return "flare";
	}
}
