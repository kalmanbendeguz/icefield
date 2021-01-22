package player;

import field.Field;

/** 
 * Mozgatható osztályok ős absztrakt osztálya
 */
public abstract class Moveable
{

	/**
 	 * Field, amin a Moveable tartózkodik
 	 */
	protected Field currentField; 
	
	
	/** 
	 * @param direction irány amerre mozgatjuk
	 */
	//A játékos lép egyet a paraméternek megadott dir irányba.
	public abstract void Move(int direction);
	
	
	/** 
	 * currentField gettere
	 * @return Field
	 */
	public Field getCurrentField() {
			return currentField;
	}
		
	
	/** 
	 * currentField settere
	 * @param nextField
	 */
	public void setCurrentField(Field nextField) {
		currentField = nextField;
	}
}