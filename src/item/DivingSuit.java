package item;

import player.Player;

/** 
 * A buvarruha megvalositasa, az ezt birtoklo es viselo szereplo nem hal meg, ha vizbe esik
 */
public class DivingSuit extends Item implements java.io.Serializable {
	
	/** 
	 * A buvarruha hasznalatanak megvalositasa. Hasznalatkor a szereplo felveszi magara a ruhat.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		player.setWears_suit(true);
		System.out.println("player now wears divingsuit");
		
	}
	
	/** 
	 * A buvarruha nevevel ter vissza(divingsuit), azonositashoz kell
	 * @return divingsuit (string)
	 */
	@Override
	public String getName() {
		return "divingsuit";
	}

}
