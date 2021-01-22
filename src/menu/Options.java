package menu;

import java.util.Scanner;
import java.util.Vector;

import field.Field;
/**
 * A beállításokat megvalósító osztály
 *
 */
public class Options {
	public static Object musicIsReadyToSet = new Object();
	public static Object nameIsReadyToSet = new Object();
	/**
	 * A játékos neve amit a toplistában megjelenítünk
	 */
	private String playerName;
	/**
	 * A zene bekapcsolását jelző boolean
	 */
	private boolean music;

	/**
	 * Konstruktor
	 */
	public Options()
	{
		// default értékek
		playerName = "Player";
		music = true;
	}

	/**
	 * Függvény a menüben található opciók kiírására
	 */
	public void ShowOptionItems() {
		System.out.println("1. Player name");		
		System.out.println("2. Music");
		System.out.println("3. Back");
		System.out.println();
		System.out.println("Current state:");
		System.out.println();
		System.out.println("Player name = "+ GetPlayerName());
		System.out.print("Music = ");
		if(GetMusic())
			System.out.println("on");
		else
			System.out.println("off");
	}
	
	/**
	 * Függvény egy beállítási opció végrehajtására
	 * @param item az option enum
	 */
	public boolean ChooseOptionsItem(OptionsItem item) {
		switch(item)
		{
		case playername:
			PlayerNameOption();
			break;
		case music:
			MusicOption();
			break;
		case back:
			return false;
		default:
			System.out.println("Invalid input.");
			}
		return true;
	}
	
	/**
	 *  Játékos nevének beállítására szolgáló függvény
	 */
	public void PlayerNameOption()
	{

		System.out.println("Set the name of player.");
		synchronized(nameIsReadyToSet) {
			nameIsReadyToSet.notifyAll();
		}
		synchronized(Main.lock) {
			try {
				Main.lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Scanner in = new Scanner(System.in); 
		SetPlayerName(in.nextLine());
		System.out.println("PlayerNumber set to "+ GetPlayerName());	

	}
	/**
	 * Playername settere
	 * @param newname új playername
	 */
	public void SetPlayerName(String newname)
	{
		System.out.println("SetPlayerName(String newname) called.");		
		playerName = newname;
	}
	/**
	 * Playername gettere
	 * @return a meglévő playername
	 */
	public String GetPlayerName()
	{
		System.out.println("GetPlayerName() called.");		
		return playerName;
	}
	
	/**
	 *  Játék zenéjének beállítására szolgáló függvények
	 */
	public void MusicOption()
	{

		System.out.println("Set the music of game. (on/off)");
		synchronized(musicIsReadyToSet) {
			musicIsReadyToSet.notifyAll();
		}
		try {
		synchronized(Main.lock) {
			try {
				Main.lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Scanner in = new Scanner(System.in); 
		SetMusic(in.nextLine().equals("on")? true : false);
		System.out.print("Music set to ");
		if(GetMusic())
			System.out.println("on");
		else
			System.out.println("off");

		}	
		catch(Exception ex)
		{
			System.out.println("Incorrect input.");	
		}		
	}
	/**
	 * A zene beállításának settere
	 * @param newvalue új értéke
	 */
	public void SetMusic(boolean newvalue)
	{		
		music = newvalue;
	}
	/**
	 * A zene beállításának gettere
	 * @return a jelenlegi zenebeállítás
	 */
	public boolean GetMusic()
	{		
		return music;
	}
	
}
