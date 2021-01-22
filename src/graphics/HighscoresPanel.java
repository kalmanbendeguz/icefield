package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;

import menu.ScoreData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * A Toplista panel megvalósítása
 */
public class HighscoresPanel extends JPanel
{
    /**
	 * A panel háttere
 	 */
    private BufferedImage backGround;
    /**
	 * A panel betűtípusa
 	 */
    private Font AmaticSc;
    /**
	 * A toplista adatai
 	 */
    private Vector<ScoreData> highscores;

    /** 
     * A HighscoresPanel konstruktora 
     * @param _highscores vector aminek ScoreData elemei vannak, ezek lesznek kirajzolva
     */
    public HighscoresPanel(Vector<ScoreData> _highscores)
    {
    	this.setLayout(null);
        this.setBounds(0,0, 1200, 720);
        highscores = _highscores;
        //megpróbáljuk beolvasni a panel hátterét
        try
        {
            backGround = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\highscores.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //megpróbáljuk beolvasni a betűtípust
        try {
			AmaticSc = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"\\src\\images\\amaticsc.ttf")).deriveFont(48f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    this.setVisible(true); //láthatóvá tesszük
	    this.setFocusable(true); //fókuszáljuk
    }

    
    /** 
     * A HighscoresPanel paint függvénye, felülírja az alap JPanel függvényét
     * @param g Graphics
     */
    @Override
    public void paint(Graphics g)
    {
        g.drawImage(backGround, 0,0, this); //kirajzolja a panel hátterét
        g.setFont(AmaticSc); //beállítjuk a betűtípust
        g.setColor(new Color(198, 205, 229)); //beállítjuk a rajzolás színét
        for (int i= 0; i<highscores.size() && i < 5;i++){ //maximum a top 5 eredményt írjuk ki
            int n = highscores.get(i).getName().length() + 7; //formázáshoz kell
            /* kirajzoljuk a highscores i-edik elemének a name változó értékét (játékos neve) és
               és score score változó értékét (körök száma ami alatt nyert)
            */
            g.drawString(highscores.get(i).getName()+ " - " + highscores.get(i).getScore() + 
            " turns", (int)(410+133-((n*(3.0f+(n-10)*0.3f)))) , 290+i*68);
            
        }
    }

    /** A háttér gettere
     * @return BufferedImage háttérkép
     */
    public BufferedImage getBackGround() {
        return backGround;
    }

    
    /** A háttér settere
     * @param backGround háttérkép
     */
    public void setBackGround(BufferedImage backGround) {
        this.backGround = backGround;
    }

    
    /** A kirajzoláshoz szükgéses font gettere
     * @return Font font
     */
    public Font getAmaticSc() {
        return AmaticSc;
    }
    
    /** A kirajzoláshoz szükséges font settere
     * @param amaticSc font
     */
    public void setAmaticSc(Font amaticSc) {
        AmaticSc = amaticSc;
    }

}
