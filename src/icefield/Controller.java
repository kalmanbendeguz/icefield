package icefield;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import field.Field;
import menu.Main;
import player.Player;
import player.PolarBear;
import test.TestFunctions;

/** 
 * A játékvezérlőnek az implementálása
 */
public class Controller implements java.io.Serializable{
	/**
	 * Azt jelzi, hogy a játékot elvesztettük-e
	 */
	private boolean lose = false;
	
	/**
	 * Azt jelzi, hogy a játékot megnyertük-e
	 */
	private boolean won = false;
	
	/**
	 * A pálya betöltését jelző szinkronizációs objektum
	 */
	public static Object mapLoaded = new Object();
	
	/**
	 * A játék végét jelző szinkronizációs objektum
	 */
	public static Object gameEnded = new Object();
	
	/**
	 * A tesztparancs fogadására kész állapot jelzésére szolgáló szinkronizációs objektum
	 */
	public static Object readyForTestCommand = new Object();
	
	/**
	 * A parancs végrehajtásának és hatásainak befejeződésének jelzésére használt szinkronizációs objektum
	 */
    public static Object commandDone = new Object();
    
	/** 
    * A pályán levő Field-ek tárolója
    */
    private Vector<Field> fields = new Vector<>();
    
    /** 
    * A pályán levő Playerek-ek tárolója
    */
    private Vector<Player> players = new Vector<>();
    
    /**
     * A jelenlegi játékos
     */
    private Player currentPlayer= null;
    
    /** 
    * A jegesmedve
    */
    private PolarBear polarBear = null;
    
    /** 
    * A játék végét jelző változó
    */
    private boolean ended = false;
    
    /**
     * Véletlen számok generálására használt objektum
     */
    private transient Random random = new Random();
    
    /** 
    * Teszt módot jelző változó
    */
    private boolean testMode = false;
    
    /** 
    * Objektum, ami tartalmazza teszt funkciókat.
    */
    private TestFunctions test = new TestFunctions(this);

    /**
     * lose tagváltozó gettere
     * @return lose (igaz, ha vesztettünk)
     */
    public boolean getLose() {
    	return lose;
    }
    
    /**
     * won tagváltozó gettere
     * @return won (igaz, ha nyertünk)
     */
    public boolean getWon() {
    	return won;
    }
    
    /**
     * Azt adja vissza, hogy elvesztettük-e már a játékot, azaz megevett-e a jegesmedve, vagy vízbe estünk, vagy az életpontunk 0-e
     * @return true, ha elvesztettük a játékot
     */
    public boolean checkIfGameLost() {
    	if(players.indexOf(currentPlayer) == players.size() - 1) {
    	   for (Field field : fields) {
	            if (field.getIsUpsideDown()) { //ha felfordult, akkor megnézzük, hogy minden játékoson van-e búvárruha
	                ArrayList<Player> playersFell = field.getPlayers(); 
	            	for(int j = 0; j < playersFell.size() && !ended; ++j) {
	            		if(!playersFell.get(j).getWears_suit()) { //ha nincs, akkor játék vége
	            			return true;
	            		}
	            	}
	            }
               
    	   }
    	}
 
        if(lose) { // egyéb okokból történő vesztés, amiket más függvények állítanak
        	return true;
        }
        return false;
    }
    

    /** 
     * fields gettere
     * @param value
     * @return Vector<Field>
     */
    public Vector<Field> getFields() { return fields;}
    
    
    /** 
     * visszaadja a jelenlegi játékost
     * @param value
     * @return Vector<Player>
     */
    public Player getCurrentPlayer() {return currentPlayer; }
    
    /** 
     * players gettere
     * @param value
     * @return Vector<Player>
     */
    public Vector<Player> getPlayers() {return players; }
    
    /** 
     * polarBear getter
     * @param value
     * @return PolarBear
     */
    public PolarBear getPolarBear() {return polarBear;}
    
    /** 
     * random gettere
     * @param value
     * @return Random
     */
    public Random getRandom() {return random; }
    
    /** 
     * ended getter
     * @param value
     * @return boolean
     */
    public boolean getEnded() {return ended;}
    
    /** 
     * testMode getter
     * @param value
     * @return boolean
     */
    public boolean getTestMode() {return testMode;}
    
    /** 
     * TestFunctions gettere
     * @param value
     * @return TestFunctions
     */
    public TestFunctions getTestFunctions() {return test;}

    /** 
     * fields settere
     * @param value
     */
    public void setFields(Vector<Field> thing) {fields = thing; }
    
    /** 
     * players settere
     * @param value
     */
    public void setPlayers(Vector<Player> thing) { players = thing; }
    
    /** 
     * polarBear settere
     * @param value
     */
    public void setPolarBear(PolarBear thing) { polarBear = thing;}
    
    /**
     * random settere 
     * @param value
     */
    public void setRandom(Random thing) { random = thing; }
    
    /** 
     * ended settere
     * @param value
     */
    public void setEnded(boolean thing) { ended = thing; }
    
    /**
     * lose settere
     * @param value
     */
    public void setLose(boolean value) { lose = value;}
    /** 
     * testMode settere
     * @param value
     */
    public void setTestMode(boolean value) {
    	testMode = value;
    	System.out.println("Testmode: " + testMode);
    }

    /** 
     * Üres konstruktor serializáláshoz
     */
    public Controller(){}

    /** 
     * A játékban szereplő hóviharokat lebonyolító függvény
     */
    private void SnowStorm()                                            
    {
    		System.out.print("Snowstorm on fields: ");
    		for (int i=1;i<(fields.size())/5;i++)
            {
                //mezo kivalasztasa
                int fieldIndex = random.nextInt(fields.size());
                System.out.print( (fieldIndex+1) +" ");
                //horeteg novelese
                fields.get(fieldIndex).IncrementSnow();

                //jatekosok testhojenek csokkentese, ha nincsenek sátorban vagy iglooban
                if (!fields.get(fieldIndex).getHasIgloo() && !fields.get(fieldIndex).getHasTent()){
                    for (Player player : fields.get(fieldIndex).getPlayers())
                    {
                        player.decrementHealth();
                    }
                }
            }
    		System.out.println();
        
    }

    
    /** 
     * Játékot indító függvény
     * @return int
     */
    public int Start()
    { 

    	boolean felt = true;
    	String filename = "";
    	while(felt) //ki lehet választani a milyen pályán szeretnénk játszani
    	{
	    	System.out.println("Please enter the map's filename. (tesztpalya/focilabda/nagypalya)");
	    	try {
	    		synchronized(Main.lock) {
	    			try {
	    				Main.lock.wait();
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    		}
	    		Scanner scan = new Scanner(System.in);
	    		filename = scan.nextLine();
	    	if (filename.contentEquals("tesztpalya")||filename.contentEquals("focilabda")||filename.contentEquals("nagypalya"))
	    		felt = false;
	    	}
	    	catch(Exception ex){}
    	}
    	ReadController(filename);// beolvassuk az xml filebol a mappat
        System.out.println(filename+" beolvasva.");     
        int i = GameLoop();
        this.players.clear();
        this.polarBear = null;
        return i;
    }
    
    /** 
     * Visszatér, hogy a játékosok egy mezőn vannak-e
     * @return boolean
     */
    private boolean ArePlayersTogether()        //Azt vizsgalja egy mezon vannak-e a jatekosok
    {
        Field previous = players.get(0).getCurrentField();    //az elozo vizsgalt jatekos fieldje
        for (Player player : players)
        {
            if (player.getCurrentField() != previous)
                return false;
        }
        return true;
    }
    
    /** 
     * Visszatér, ha a rakétaalkatrészek egy mezőn vannak
     * @return boolean
     */
    private boolean ArePartsTogether()
    {
        int partsOnField = 0;
        for (Field field : fields) {
            for (Player player : players) {
                if (player.getCurrentField() == field)
                    partsOnField +=  player.RocketParts();
            }
            if(partsOnField == 3)
            return true;
        }
        return false;
    }
    
    /**
     * A játék megnyerését leellenőrző függvény
     * @return true, ha el tudjuk sütni a rakétát
     */
    public boolean TryFireWithoutWaiting() {
        //A játékosok és a rakétaalkatrészek mind egy mezőn vannak
        if(ArePartsTogether() && ArePlayersTogether()) {
            won = true;
            ended = true;
            return true;
        }
        return false;
    }

    /** 
     * Megpróbálja elsütni a rakétát
     * @return boolean
     */
    private boolean TryFire()
    {
        //A játékosok és a rakétaalkatrészek mind egy mezőn vannak
        if(ArePartsTogether() && ArePlayersTogether()) {
            System.out.println("You Won!");
            won = true;
    		synchronized(gameEnded) {
    			gameEnded.notifyAll();
    		}
    		//késleltetés
    		synchronized(Main.lock) {
    			try {
    				Main.lock.wait();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    		Scanner in = new Scanner(System.in); 
    		in.next();
            ended = true;
            return true;
        }
        return false;
    }

    /** 
     * Befejezi a játékmenet futását és a csapat vesztett
     */
    public void Finish()
    {
        System.out.println("You Lose!");
        lose = true;
       
		synchronized(gameEnded) {
			gameEnded.notifyAll();
		}
		
		//késleltetés
		synchronized(Main.lock) {
			try {
				Main.lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Scanner in = new Scanner(System.in); 
		in.next();

		ended = true;
    }
    
    /**
     * Várakozás nélkül fejezi be a játékot, azaz nem vár a felhasználótól nyugtázásra
     */
    public void FinishWithoutWaiting() {
    	lose = true;
    	ended =true;
    }

    
    /** 
     * A játék ciklusa, mindaddig fut ameddig nem nyer a csapat, vagy meg nem hal valaki.
     * @return int visszatér a végzett játékmenet körök számával
     */
    private int GameLoop() 
    {
    	lose= false;
    	won = false;
        int numOfTurns = 0;
        String input_test;

		/* Ha olyan palyat toltottunk be amin nincs alapbol szereplo, akkor kotelezoen le kell rakni egyet,
		 * kulonben nem mukodik a jatek, mert nincs kinek parancsot adni. */
        while(players.size() == 0) { 
			System.out.println("No players on map, please put down a player:");
			synchronized(readyForTestCommand) {
				readyForTestCommand.notifyAll();
	        }
			synchronized(Main.lock) {
				try {
					Main.lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Scanner scan = new Scanner(System.in);
			input_test = scan.nextLine();
			test.testCommand(input_test);
			synchronized(commandDone) {
				commandDone.notifyAll();
	        }
		}
        
        /* Miutan mar van jatekos a palyan, donthetunk, hogy teszt modban kezdjuk-e a jatekot. */
        System.out.println("TEST MODE? (You can switch later) (Y/N)");
        synchronized(mapLoaded) {
			mapLoaded.notifyAll();
        }
		synchronized(Main.lock) {
			try {
				Main.lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Scanner scan = new Scanner(System.in);
		input_test = scan.nextLine();
		input_test = input_test.toUpperCase();
		if(input_test.contentEquals("Y")) {
			testMode = true;
			/* Ha teszt modban kezdunk, van lehetosegunk a jatek kezdete elott barmennyi tesztparancsot kiadni,
			 * igy barmit letehetunk barhova (eszkozoket, jatekosokat, iglut, satrat), mozgathatjuk a jegesmedvet,
			 * hoviharokkal beallithatjuk a homennyisegeket az egyes mezokon */
			System.out.println("Type test commands and start the game with \"start\"");
			synchronized(mapLoaded) {
				mapLoaded.notifyAll();
	        }
			/* A kezdeti tesztparancsok utan "start"-ra indul a jatek */
			while(!input_test.contentEquals("start")) {
				synchronized(readyForTestCommand) {
					readyForTestCommand.notifyAll();
		        }
				synchronized(Main.lock) {
					try {
						Main.lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Scanner scan1 = new Scanner(System.in);
				input_test = scan1.nextLine();
				test.testCommand(input_test);
			}
			
		} else if(input_test.contentEquals("N")) {
			/* Ha nem teszt modban inditjuk a jatekot, akkor nincsenek jatek eleji teszt parancsok,
			 * a jatek azonnal kezdodik. Kesobb barmikor atvalthatunk teszt modba. */
			System.out.println("Ok, game starts");
		} else {
			System.out.println("Invalid command, testmode off");
		}
        
        while (!ended) {
            for (int i = 0; i < players.size() && !ended; i++) { //egymás után jönnek a játékosok
                System.out.println("Player " + (i + 1) + "'s turn");
                System.out.println("Player type: " + players.get(i).getName()); //kiírjuk a játékos típusát
                System.out.println("health: " + players.get(i).getHealth()); //és a health-jét
                currentPlayer = players.get(i);
                
                synchronized(mapLoaded) {
        			mapLoaded.notifyAll();
                }
                players.get(i).Turn(); //elindítjuk a játékos körét
                for(Field field : fields) {
                	field.SetIsInspected(false);
                }
                numOfTurns++;
                if(TryFire()) return numOfTurns; //megpróbáljuk elsütni a rakétát, 
                //ha sikerül visszatérünk a körök számával


                if (i == players.size()) //ha elér az utolsó játékosig, akkor vissza megy a players vektor elejére
                    i = 0;
            }
            
            if(!testMode) { //ha nem vagyunk testMode-ban, akkor hívodík a SnowStorm 
            	SnowStorm();
            	if(polarBear != null) { //és ha van jegesmedve, akkor mozgatjuk véletlenszerű irányba
                	int maxBearDir = polarBear.getCurrentField().getNeighbors().size();
                	int bearDir = random.nextInt(maxBearDir);
                	polarBear.Move(bearDir);
            	}

            }
            
            //végignézzük a mezőket, hogy van-e közöttük, ami fel van fordulva
            for (Field field : fields) {
                if (field.getIsUpsideDown()) { //ha felfordult, akkor megnézzük, hogy minden játékoson van-e búvárruha
                    ArrayList<Player> playersFell = field.getPlayers(); 
                	for(int j = 0; j < playersFell.size() && !ended; ++j) {
                		if(!playersFell.get(j).getWears_suit()) { //ha nincs, akkor játék vége
                			Finish();
                		}
                	}
                }
                if(field.getHasTent()) { //ha van egy mezőn sátor, akkor levesszük onnan
                	field.setHasTent(false);
                	System.out.println("Tent removed from field " + (fields.indexOf(field)+1));
                }
                   
            }   
        }
        return -1;
    }
    
    /** 
     * Függvény a controller kiírására
     * @param palya megadjuk az xml fajl nevet (.xml nelkul)
     */
  	public void WriteController(String palya)
  	{
  	  	FileOutputStream fos;
  		try {
  			fos = new FileOutputStream(System.getProperty("user.dir")+"\\src\\"+palya+".xml");
  		 	XMLEncoder encoder = new XMLEncoder(fos);
  		  	encoder.writeObject(this);
  		  	encoder.close();
  		  	fos.close();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}

  	}
  	
      /** 
       * Függvény a controller beolvasására
       * @param palya stringben megadjuk az xml fajl nevet (.xml nelkul)
       */
  	public void ReadController(String palya )
  	{
  	  	if (new File(System.getProperty("user.dir")+"\\src\\"+palya+".xml").exists()==false) // ha nem létezik még a fájl
  	  	{
  			System.out.println(System.getProperty("user.dir")+"\\src\\"+palya+".xml file does not exists.");
  			return;
  	  	}
  		try {
  			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\"+palya+".xml");
  		  	XMLDecoder decoder = new XMLDecoder(fis);
  		  	Controller copy = (Controller) decoder.readObject();
  		  	this.ended = copy.getEnded();
  		  	this.players = copy.getPlayers();
  		  	this.fields = copy.getFields();
  		  	won = lose = testMode = false;
  		  	for(Player p : players)
  		  		p.setController(this);
  		  	this.polarBear = copy.getPolarBear();
  		  	if (polarBear != null)
  		  		this.polarBear.setController(this);
  		  	decoder.close();
  		  	fis.close();
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
}