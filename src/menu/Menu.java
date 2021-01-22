package menu;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Vector;

import icefield.Controller;
/**
 * A menüt megvalósító osztály
 */
public class Menu {
	/**
	 *  a toplistát tároló tömb
	 */
    private Vector<ScoreData> highscores; 
	
	public Vector<ScoreData> getHighscores(){
		return highscores;
	}
	public void setHighscores(Vector<ScoreData> value){
		highscores = value;
	}
	
	/**
     * a beállítások osztálya
     */
	private Options options; 
	/**
	 * a zenelejátszó osztály
	 */
	private AudioPlayer audioPlayer; 
	
	/**
	 * A controller osztály egy példánya
	 */
	private Controller controller; 
	/**
	 * Konstruktor
	 */
	public Menu(){
		this.controller = new Controller();
		highscores = new Vector<>();
		options = new Options();
		audioPlayer = new AudioPlayer();
		audioPlayer.Start(true);
		ReadHighscores();
	}
	
	/**
	 * Controller gettere
	 */
	public Controller getController() {return controller;}
	
	public Options getOptions() {
		return this.options;
	}
	
	/**
	 * Függvény a menüben található opciók kiírására
	 */
	public void ShowMenuItems() {
		System.out.println("1. New Game");		
		System.out.println("2. Settings");
		System.out.println("3. Show Best Scores");
		System.out.println("4. Exit");
	}
	/**
	 * Függvény egy menüopció végrehajtására
	 * @param item menüopció
	 * @return false ha kilépünk a játékból
	 */
	public boolean ChooseMenuItem(MenuItem item) {
		switch(item)
		{
		case newgame:
			NewGame();
			break;
		case settings:
			Settings();
			break;
		case showbestscores:
			ShowBestScores();
			break;
		case exit:
			Exit();
			return false;
		default:
			System.out.println("Incorrect input.");	
			break;
		}

		return true;
	}
	/**
	 * Függvény a játék elindításához
	 */
	public void NewGame() {
		audioPlayer.Stop();
		if(options.GetMusic())
			audioPlayer.Start(false);
		
		int turns = controller.Start();
		if (turns>0){
			AddHighscore(turns);
		}
		audioPlayer.Stop();
		if(options.GetMusic())
			audioPlayer.Start(true);
	}
	/**
	 * Függvény a beállításokhoz
	 */
	public void Settings() {
		
		boolean felt= true;
		while(felt)
			{
			options.ShowOptionItems();
	
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
				OptionsItem o=	OptionsItem.values()[in.nextInt()-1];
				felt = options.ChooseOptionsItem(o);
				audioPlayer.Reset(options.GetMusic());

			}
			catch(Exception ex){
				System.out.println("Invalid input.");
			}
		}
	}
	/**
	 * Függvény a toplista megjelenítésére
	 */
	public void ShowBestScores() {
		// rendezés
		Collections.sort(highscores, new Comparator<ScoreData>() {
		    @Override
		    public int compare(ScoreData o1, ScoreData o2) {
		        return Integer.compare(o1.getScore(), o2.getScore());
		    }
		});
		if(highscores.isEmpty()) //  ha üres a tömb tudatjuk a felhasználóval
			System.out.println("There are no highscores yet.");
		else
		{
			for(int i=0; i< highscores.size(); i++)
				System.out.println((i+1)+". " + highscores.get(i).getName() + " - " +highscores.get(i).getScore() );
		}
		//késleltetés
		synchronized(Main.lock) {
			try {
				Main.lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*synchronized(Main.lock) {
			try {
				Main.lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		Scanner in = new Scanner(System.in); 
		in.next();
	}
	/**
	 * Függvény a játékból történő kilépéshez
	 */
	public void Exit() {
		WriteHighscores();
	}
	/**
	 * Új highscore felvitelére szolgáló függévény
	 * @param score az új toplista elem értéke
	 */
	public void AddHighscore(int score)
	{		
		System.out.println("New highscore added.");		
		highscores.add(new ScoreData(options.GetPlayerName(), score));
	}
	/**
	 * Függvény a toplista kiírására
	 */
	public void WriteHighscores()
	{
	  	FileOutputStream fos;
		try {
			fos = new FileOutputStream(System.getProperty("user.dir")+"\\src\\highscores.xml");
		 	XMLEncoder encoder = new XMLEncoder(fos);
		  	encoder.writeObject(highscores);
		  	encoder.close();
		  	fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Függvény a toplista beolvasására
	 */
	public void ReadHighscores()
	{
	  	if (new File(System.getProperty("user.dir")+"\\src\\highscores.xml").exists()==false) // ha nem létezik még a fájl
	  	{
	  		System.out.println(System.getProperty("user.dir")+"\\src\\highscores.xml file does not exists.");
	  		return;
	  	}
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\highscores.xml");
		  	XMLDecoder decoder = new XMLDecoder(fis);
		  	highscores = (Vector<ScoreData>) decoder.readObject();
		  	decoder.close();
		  	fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
