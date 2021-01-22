package player;

import java.util.Scanner;

import field.Field;
import icefield.Controller;
import menu.Main;
import test.TestFunctions;


/** 
 * Kutató szereplő osztálynak a megvalósítása 
 */
public class Scientist extends Player{
	

	/** 
	 * A Scientist konstruktora
	 * @param c a játék kontrollere
	 * @param startField a kutató kezdeti mezője
	 */
	public Scientist(Controller c, Field startField) {
		super(c, startField);
		this.maxHealth = 4;
		this.health = this.maxHealth;
		name = "scientist"; //itt a nevet beállítjuk a osztály nevére
	}

	/** 
	 * Üres konstruktor serializáláshoz
	 */
	public Scientist(){
		maxHealth = 4;
	}
	/** 
	 * Megvizsgálja, hogy mennyi játékost bír el egy paraméterben megadott szomszédos Field, majd kiírja
	 */
	public void InspectField(Field field) {
		if (this.energy > 0) { //energiába kerül
			field.SetIsInspected(true);
			if (field.getMaxWeight() == 10) //a 10 teherbírású mező stabil
				System.out.println("Field is stable");
			else
				System.out.println("Field can hold: " + field.getMaxWeight() + " players");
			this.decrementEnergy();
		}else {
			System.out.println("Not enough energy");
		}
		
		
	}
	/** 
	 * Az Scientist egy körének a függvénye
	 */
	@Override
	public void Turn() {
		String input;
		energy = 4;
		boolean endturn = false;
		while(!endturn) { //addig van ciklusban, ameddig a játékos "end turn"-t nem ír, tehát a kör végéig
			synchronized(Main.lock) {
				try {
					Main.lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Scanner scanner = new Scanner(System.in); //olvassa a standard inputot
			input = scanner.nextLine();
			switch (input) {
				case "end turn": endturn = true;//kör vége
					break;
				case "list inventory": ListItems(); //a felhasználó kilistázza a játékos eszköztárában 
					break;							//található item-eket
				case "remove snow": RemoveSnow(); //eltávolít a mezőről a játékos 1 egységnyi havat
					break;
				case "pick up item": PickItemUp(); //felveszi a legelső tárgyat a mezőről
					break;
				case "menu": controller.FinishWithoutWaiting(); endturn = true; //kilép a menube
					break;
				case "neighbors": System.out.println(currentField.getNeighbors().size()); //megnézi hány szomszédja van a currentFieldnek
					break;
				case "peek": if (currentField.getSnow() == 0) System.out.println(currentField.GetItem().getName());
									else System.out.println("Field is covered with snow!");
							//megnézi milyen item van a currentFielden
					break;
				case "test on": controller.setTestMode(true); //bekapcsolódik a test mode
					break;
				case "test off": controller.setTestMode(false); //kikapcsolódik a test mode
					break;
				default: if(input.matches("^use\\s\\w*")) { //reguláris kifejezés egy tárgy használata parancs felismeréséhez
							UseItem(input); break;
						}else if(input.matches("^move\\s\\d+")){ //reguláris kifejezés lépés parancs felismeréséhez
							String[] temp = input.split(" ");
							Move(Integer.parseInt(temp[1])-1); break;
						}else if(input.matches("^inspect\\s\\d+")){ //reguláris kifejezés InspectField funkcióhoz
							String[] temp = input.split(" ");
							try{
								InspectField(currentField.GetNeighbor(Integer.parseInt(temp[1])-1));
							}catch(Exception e){
								System.out.println("No such neighbor!");
							}
							break;
						}else{
							if (controller.getTestMode())
								controller.getTestFunctions().testCommand(input);//ha be van kapcsolva a teszt mód,
								//akkor, ami inputot nem ismer fel a Turn(), azt átadja a testCommand() függvénynek
							else
								System.out.println("Invalid command!");
							break;
						}
			}
			//TestFunctions tf = new TestFunctions(this.controller);
			//tf.viewMap();
		}
	}

}
