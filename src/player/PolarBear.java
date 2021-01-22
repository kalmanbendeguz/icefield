package player;

import field.Field;
import icefield.Controller;

/** 
 * Jegesmedve osztálynak a megvalósítása 
 */
public class PolarBear extends Moveable implements java.io.Serializable
{

	/**
	 * A PolarBear megkapja a Controllert is a konstruktorban, hogy tudja a Controller Finish()
	 * függvényét hívni
 	 */
	private Controller controller;


	/** 
	 * A PolarBear konstruktora
	 * @param _currentField a jegesmedve kezdeti mezője
	 * @param _controller a játék kontrollere
	 */
	public PolarBear(Field _currentField, Controller _controller){
		currentField = _currentField;
		controller = _controller;
		currentField.setPolarBear(this); //currentField polarBear attribútumát beállítja
		controller.setPolarBear(this); //controller polarBear attribútumát beállítja
	}

	/**
	 * Serializáláshoz szükséges üres konstruktor
 	 */
	public PolarBear(){

	}

	
	/** 
	 * Megvalósítja a Moveable Move függvényét, mozgatni lehet a pályán a PolarBear-t
	 * @param dir irány amelyik szomszédra fog mozogni
	 */
	public void Move(int dir) {
		currentField.Pass(dir, this);	
		//ha a fielden van játékos és nincs rajta igloo, akkor Finish-t hív
		if (currentField.getPlayers().size() != 0 && !currentField.getHasIgloo()){
			System.out.println("You became polarbear's dinner!");
			//controller.setLose(true);
			controller.Finish();
		}
	}

	
	/** 
	 * controller gettere
	 * @return Controller
	 */
	public Controller getController(){
		return controller;
	}
	
	
	/** 
	 * controller settere
	 * @param _controller
	 */
	public void setController(Controller _controller){
		controller = _controller;
	}

}