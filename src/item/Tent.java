package item;

import field.Field;
import player.Player;

/** 
 * Sator megvalositasa. Ugyanazt tudja mint az iglu (megov a hoviharoktol), de lehet instabil mezore is epiteni es a jegesmedve ellen nem ved.
 */
public class Tent extends Item implements java.io.Serializable {
	
	/** 
	 * A sator hasznalatanak megvalositasa. A szereplo mezojere sator kerul, amennyiben ott nincs iglu vagy masik sator.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		/* Sator allitas */
		if(!player.getCurrentField().getHasTent()) {
			player.getCurrentField().setHasTent(true);
			player.RemoveItem(this);
			System.out.println("Tent set");
			/* Egy energiaba kerul */
			player.decrementEnergy();
		} else {
			System.out.println("Field already has tent");
		}
		
	}
	
	/** 
	 * A sator nevevel ter vissza(tent), azonositashoz kell
	 * @return tent (string)
	 */
	@Override
	public String getName() {
		return "tent";
	}
}
