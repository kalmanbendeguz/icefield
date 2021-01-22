package item;

import java.util.ArrayList;

import field.Field;
import player.Moveable;
import player.Player;

/** 
 * Kotel megvalositasa. Ennek hasznalataval a szereplo megmentheti a mellette levo vizbe esett tarsat.
 */
public class Rope extends Item implements java.io.Serializable{

	/** 
	 * A kotel hasznalatanak megvalositasa. Hasznalatkor a szereplo megment minden vizbe esett szereplot akik a hasznaloval szomszedosan helyezkednek el. Ekkor a megmentettek atkerulnek a megmento mezojere.
	 * @param player A szereplo, aki hasznalja
	 */
	@Override
	public void Use(Player player) {
		
		Field current = player.getCurrentField(); /* Ez a mező lesz az, ahol a szereplőnk van. */
		Field saveFrom = current; /* Erről a mezőről mentjük meg a vízbe esett szereplőt (a lyukas mező). Ha nem találunk megmentendő szereplőt, akkor a current marad, innen tudjuk majd, hogy nem kell csinálni semmit. */
		
		int savedplayers = 0;
		
		for(Field f : current.getNeighbors()) { //a szomszedos mezokre
			
			if(f.getIsUpsideDown()) { //megnezzuk hogy atfordult-e

				saveFrom = f; //ha igen, akkor innen mentunk jatekosokat
				ArrayList<Player> playersToSave = saveFrom.getPlayers();
				while(!playersToSave.isEmpty()) { //sorban kimentjuk az osszeset
					Player p = playersToSave.get(0);
					int dirToSafety = saveFrom.getNeighbors().indexOf(current);
					saveFrom.Pass(dirToSafety,p);
					savedplayers++;
				}
				f.setIsUpsideDown(false); //a mezot visszaforditjuk
			}

		}

		player.decrementEnergy(); // egy energiaba kerul
		System.out.println(savedplayers + " player(s) saved");
		System.out.println("energy level: " + player.getEnergy());
	}
	
	/** 
	 * A kotel nevevel ter vissza(rope), azonositashoz kell
	 * @return rope (string)
	 */
	@Override
	public String getName() {
		return "rope";
	}

}
