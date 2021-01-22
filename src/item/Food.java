package item;

import player.Player;

/** 
 * Az elelem megvalositasa, az ezt hasznalo szereplonek no az eletereje
 */
public class Food extends Item implements java.io.Serializable {

	/** 
	 * Az etel hasznalata. A hasznalojanak noveli az eletpontjait eggyel, amennyiben az meg nincs a maximumon
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		player.incrementHealth();
		player.RemoveItem(this); 
	}

	/** 
	 * Az etel nevevel ter vissza(food), azonositashoz kell
	 * @return food (string)
	 */
	@Override
	public String getName() {
		return "food";
	}
}
