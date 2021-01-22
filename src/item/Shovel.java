package item;

import field.Field;
import player.Player;


/** 
 * Lapat megvalositasa. Ennek hasznalataval a szereplo egy egyseg energiaval ket egyseg havat takarithat el a mezorol.
 */
public class Shovel extends Item implements java.io.Serializable {
	
	/** 
	 * A lapat hasznalatanak megvalositasa. A szereplo ketto havat eltakarit, vagy 1/0-at, ha a mezon csak annyi van. Egy energiaba kerul.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		
		Field field = player.getCurrentField(); /* Ezen a mezőn fogunk havat takarítani, ezt a player-től kérdezzük le. */
		int snowCount = field.getSnow();
		if(snowCount > 1) {                 /* Ha több, mint egy egység hó van a mezőn, */
			snowCount -= 2;					/* akkor két egységet takarítunk el. */
			System.out.println("2 snow removed");
		} else if (snowCount == 1){			/* Ha 1 egység van, */
			snowCount--;					/* akkor azt az egy egységet eltakarítjuk. */
			System.out.println("1 snow removed");
		} else if (snowCount <= 0) { 		/* Ha nincs hó a jégtáblán, */
			System.out.println("0 snow removed"); /* akkor nem csinálunk semmit. */
		}		
		field.setSnow(snowCount);			
		
		player.decrementEnergy(); /* Az ásóval ásás a szereplőnek egy energiájába kerül. */
	}
	
	/** 
	 * A lapat nevevel ter vissza(shovel), azonositashoz kell
	 * @return shovel (string)
	 */
	@Override
	public String getName() {
		return "shovel";
	}
}
