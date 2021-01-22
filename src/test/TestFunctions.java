package test;

import java.util.Scanner;

import field.Field;
import icefield.Controller;
import item.Charge;
import item.DivingSuit;
import item.Flare;
import item.Food;
import item.Gun;
import item.Item;
import item.Rope;
import item.Shovel;
import item.Spade;
import item.Tent;
import player.Eskimo;
import player.Player;
import player.PolarBear;
import player.Scientist;


public class TestFunctions
{
	/** 
	 * Az adott jatek kontrollere
	 */
    private Controller controller;

    /** 
	 * Konstruktor, ahol beallitjuk a kontrollert
	 * @param c A kontroller
	 */
    public TestFunctions(Controller c)
    {
        controller = c;
    }

    /** 
	 * Feldolgozza a tesztparancsot, es a megfelelo fuggvenyt hivja. Lekezeli az ervenytelen parancsot is.
	 * @param input A feldolgozando parancs, jellemzoen par szavas sztring
	 */
	public void testCommand(String input) { //A Player egy körének a függvénye
		System.out.println("(TEST)");
		try {
			//input = scanner.nextLine();
			String[] command = input.split("\\s+");
			if(command[0].contentEquals("view") && command[1].contentEquals("map")) {
				viewMap();
			} else if (command[0].contentEquals("spawn")) {
				if( command[1].contentEquals("eskimo") || command[1].contentEquals("scientist")) {
					Item item = null;
					if(command.length == 4) {
						if(command[3].contentEquals("shovel") ) {
							item = new Shovel();
						} else if (command[3].contentEquals( "spade")) {
							item = new Spade();
						} else if(command[3].contentEquals("food")) {
							item = new Food();
						} else if(command[3].contentEquals("divingsuit") ) {
							item = new DivingSuit();
						} else if(command[3].contentEquals("rope") ) {
							item = new Rope();
						} else if(command[3].contentEquals("tent") ) {
							item = new Tent();
						} else if(command[3].contentEquals("charge") ) {
							item = new Charge();
						} else if(command[3].contentEquals("flare") ) {
							item = new Flare();
						} else if(command[3].contentEquals("gun") ) {
							item = new Gun();
						}
					}
					int field = Integer.parseInt(command[2]) - 1;
					
					Field f = controller.getFields().get(field); // egyelőre nincs hibakezelés
					if(f.getPlayers().size() == f.getMaxWeight()) {
						System.out.println("Field overweight, player cannot be spawned");
					} else if(command[1].contentEquals("eskimo") ) {
						SpawnEskimo(f,item);
					} else if(command[1].contentEquals("scientist") ) {
						SpawnScientist(f, item);
					}
					
				} else if(command[1].contentEquals("polarbear") ) {
					int field = Integer.parseInt(command[2]) - 1;
					Field f = controller.getFields().get(field); // egyelőre nincs hibakezelés
					SpawnPolarBear(f);
				} else if(command[1].contentEquals("item") ) {
					Field f  = new Field();	
					int field = Integer.parseInt(command[3]) - 1;
					if(controller.getFields().size() - 1 < field ) {
						System.out.println("No such field!");
					}else {
						f = controller.getFields().get(field);
					}
					
					
					if(command[2].contentEquals("shovel") ) {
						SpawnItem(f, new Shovel());
					}else if (command[2].contentEquals("spade") ) {
						SpawnItem(f, new Spade());
					} else if(command[2].contentEquals("food")) {
						SpawnItem(f, new Food());
					}else if(command[2].contentEquals("divingsuit") ) {
						SpawnItem(f, new DivingSuit());
					}else if(command[2].contentEquals("rope") ) {
						SpawnItem(f, new Rope());
					} else if(command[2].contentEquals("tent") ) {
						SpawnItem(f, new Tent());
					} else if(command[2].contentEquals("charge") ) {
						SpawnItem(f, new Charge());
					} else if(command[2].contentEquals("flare") ) {
						SpawnItem(f, new Flare());
					} else if(command[2].contentEquals("gun") ) {
						SpawnItem(f, new Gun());
					} else {
						System.out.println("No such item!");
					}
				} else {
					System.out.println("This cannot be spawned");
				}
				
			} else if(command[0].contentEquals("polarbear") ) {
				
				int dir = Integer.parseInt(command[1]) - 1;
				if(controller.getPolarBear() != null ) {
					controller.getPolarBear().Move(dir);
				}else {
					System.out.println("No polarbear on map!");
				}
				
			} else if(command[0].contentEquals("set")){
				if(command[1].contentEquals("igloo") ) {
					int field = Integer.parseInt(command[2]) - 1;
					Field f = controller.getFields().get(field); // egyelőre nincs hibakezelés
					SetIgloo(f);
				} else if(command[1].contentEquals("tent") ) {
					int field = Integer.parseInt(command[2]) - 1;
					Field f = controller.getFields().get(field); // egyelőre nincs hibakezelés
					SetTent(f);
				} else {
					System.out.println("No such thing can be set");
				}
				
			} else if(command[0].contentEquals("snowstorm") ) {
				int howmany = command.length - 1;
				for(int i = 0; i < howmany ; ++i) {
					int field = Integer.parseInt(command[i+1]) - 1;
					Field f = controller.getFields().get(field); // egyelőre nincs hibakezelés
					SpawnSnowStorm(f);
				}
				System.out.print("Snowstorm affected fields: ");
				for(int i = 1; i < command.length; ++i) {
					System.out.print(command[i] + " ");
				}
				System.out.println();
			} else if(command[0].contentEquals("start")){
				
			} else {
				System.out.println("Invalid command!");
			}
		}  catch (Exception e) {
			System.out.println("Invalid input, maybe not a number when choosing field!");
		}	
	}
	
	/** 
	 * Az adott mezore letesz egy eszkimot es az eszkoztaraba beletesz egy adott eszkozt
	 * @param field A mezo amire letesszuk az eszkimot
	 * @param item Az eszkoz, amit az eszkimonak adunk. Ha semmit nem adunk neki, lehet null.
	 */
    public void SpawnEskimo(Field field, Item item)     //Eszkimo lehelyezese adott mezore
    {
        Eskimo eskimo = new Eskimo(controller, field);
        eskimo.AddItem(item);
        controller.getPlayers().add(eskimo);
        System.out.println("Eskimo spawned");
    }

    /** 
	 * Az adott mezore letesz egy kutatot es az eszkoztaraba beletesz egy adott eszkozt
	 * @param field A mezo amire letesszuk a kutatot
	 * @param item Az eszkoz, amit a kutatonak adunk. Ha semmit nem adunk neki, lehet null.
	 */
    public void SpawnScientist(Field field, Item item)  //Kutato lehelyezese adott mezore
    {
        Scientist scientist = new Scientist(controller, field);
        scientist.AddItem(item);
        controller.getPlayers().add(scientist);
        System.out.println("Scientist spawned");
    }

    /** 
	 * Az adott mezore letesz egy jegesmedvet. Ha mar van a palyan jegesmedve, akkor nem tudunk megegyet lerakni.
	 * @param field A mezo amire letesszuk a jegesmedvet
	 */
    public void SpawnPolarBear(Field field)             //Jegesmaci lehelyezese adott mezore
    {
    	if(controller.getPolarBear() != null) {
    		System.out.println("There's already a polarbear");
    	} else {
    		PolarBear polarbear = new PolarBear(field, controller);
    		System.out.println("Polarbear spawned");
    	}
    }

    /** 
   	 * Az adott mezore felallit egy satrat, amennyiben ott nincs mar sator vagy iglu
   	 * @param field A mezo amire felallitjuk a satrat. Ez lehet instabil mezo is.
   	 */
    public void SetTent(Field field)                  //Sator lehelyezese adott mezore
    {
        field.setHasTent(true);
        System.out.println("Tent set");
    }

    /** 
   	 * Az adott mezore felepit egy iglut, amennyiben ott nincs mar sator vagy iglu.
   	 * @param field A mezo amire felepitjuk az iglut. Csak stabil mezo lehet.
   	 */
    public void SetIgloo(Field field)                  //Iglu lehelyezese adott mezore
    {
        field.setHasIgloo(true);
        System.out.println("Igloo built");
    }

    /** 
	 * Az adott mezore letesz egy eszkozt. Ha ho van a mezon, akkor a ho ala kerul.
	 * @param field A mezo amire letesszuk az eszkozt
	 * @param item Az eszkoz
	 */
    public void SpawnItem(Field field, Item item)        //Sator lehelyezese adott mezore
    {
        field.PushItem(item);
        System.out.println("Item spawned");
    }

    /** 
   	 * Az adott mezot hoviharral sujtja, ahol a jatekosok megserulnek, ha nincsenek satorban vagy igluban. A mezore pontosan egy egyseg havat rak.
   	 * @param field A mezo amit hoviharral sujtunk.
   	 */
    public void SpawnSnowStorm(Field field)              //Iglu lehelyezese adott mezore
    {
		field.IncrementSnow();
		if (!field.getHasIgloo() && !field.getHasTent()){
        	for (Player player : field.getPlayers())
        	{
            	player.decrementHealth();
			}
		}	
    }
    
    /** 
   	 * Kiirja a kimenetre a terkep minden fontos informaciojat, azaz minden mezore: sorszam, teherbiras, mennyi ho van rajta, van-e rajta sator, van-e rajta iglu, fel van-e fordulva, milyen eszkozok vannak rajta, melyik szereplok vannak rajta, van-e rajta jegesmedve, es hogy hanyas azonositoju mezok a szomszedai.
   	 */
    public void viewMap() {
    	for(Field f: controller.getFields()) {
    		
    		System.out.println("FIELD #" + (controller.getFields().indexOf(f)+1) +": mW = " + f.getMaxWeight() + " snow=" + f.getSnow() + " tent=" +f.getHasTent()+ " igloo=" + f.getHasIgloo()+ " iUD="+f.getIsUpsideDown());
    		for(Item i: f.getItems()){
    			System.out.print(i.getName()+ " ");
    		}
    		if(f.getItems().size() == 0) {
    			System.out.print("-");
    		}
    		System.out.print("(" + f.getPlayers().size() + ")");
    		for(Player p: f.getPlayers()){
    			System.out.print(" "+p.getName());
    		}
    		System.out.println();
    		String polarbear;
       		if(f.getPolarBear() != null) {
    			polarbear = "true";
    		} else polarbear = "false";
    		System.out.print("PB: " + polarbear);
    		System.out.print("\t");
    		for(Field n: f.getNeighbors()) {
    			System.out.print((controller.getFields().indexOf(n)+1)+ " ");
    		}
    		System.out.println();
    		System.out.println();
    	}
    }
}
