package menu;
/**
 * A toplista egy elemét megvalósító osztály
 *
 */
public class ScoreData implements java.io.Serializable {

	/**
	 * A játékos neve
	 */
	private String name; 
	/**
	 * játékos pontszáma => hány kör alatt teljesítette a pályát
	 */
	private int score; 
	/**
	 * Konstruktor
	 */
	public ScoreData(){}
	/**
	 * Konstruktor
	 * @param n név
	 * @param s pontszám
	 */
	public ScoreData(String n, int s)
	{
		name = n;
		score = s;
	}
	
	/**
	 * A score settere
	 * @param newvalue új érték
	 */
	public void setScore(int newvalue)
	{
		score = newvalue; 
	}
	/**
	 * A score gettere
	 * @return a score meglévő értéke
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * A name settere
	 * @param newvalue új érték
	 */
	public void setName(String newvalue)
	{
		name = newvalue; 
	}
	/**
	 * A name gettere
	 * @return a meglévő érték
	 */
	public String getName()
	{
		return name;
	}
}
