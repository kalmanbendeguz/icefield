package graphics;

import player.Player;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import item.Spade;
/**
 * A játékos eszköztárának grafikus megjelenését biztosító osztály.
 */
public class Inventory {
    /**
	 * A játékos, amelyiknek az eszköztárát megjeleníti az osztály.
 	 */
    private Player currentPlayer;
    /**
	 * Az eszköztár háttere.
 	 */
    private BufferedImage backGround;
    /**
	 * Egy tömb, ami tárolja az eszközök gombjait.
 	 */
    private ArrayList<JButton> itemButtons;
    /**
	 * A kör végét jelző gomb.
 	 */
    private JButton endTurnButton;

    /**
	 * A Inventory konstruktora
     * @param player Az a játékos, amelyiknek az eszköztárát megjeleníti.
 	 */
    public Inventory(Player player){
        currentPlayer = player; //beállítsuk a currentPlayer változót, a paraméterben kapott Player-re
        //megpróbáljuk beállítani az Inventory hátterét
        try {
            backGround = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\inventory.png"));
		} catch (IOException e) {
			e.printStackTrace();
        }
        //inicializáljuk a eszköztár gombjainak tömbjét
        itemButtons = new ArrayList<JButton>();
        //inicializáljuk a kör végét jelző gombot és konfiguráljuk
        endTurnButton = new JButton();
        endTurnButton.setActionCommand("endturn");
        endTurnButton.setOpaque(false);
        endTurnButton.setContentAreaFilled(false);
        endTurnButton.setBorderPainted(false);
        endTurnButton.setBounds(505, 585, 183, 53);
        //létrehozzuk és beállítjuk az eszközök gombjait is, majd hozzáadjuk az ezeket tároló tömbhöz
        for (int i=0; i<5; i++)
        {
            JButton item = new JButton();
            item.setOpaque(false);
            item.setContentAreaFilled(false);
            item.setBorderPainted(false);
            item.setBounds(417 + i*80, 651, 60, 58);
            itemButtons.add(item);
        }
    }

    /**
	 * A Inventory konstruktora
     * @param p panel, amire rajzoljuk az eszköztár megjelenítését.
     * @param g Graphics osztály, amivel rajzolunk.
 	 */
    public void paint(Graphics g, JPanel p){
        //kitöltsük pirosra a játékos életpontjait jelző gömböt
        g.setColor(Color.red);
        g.fillRect(189, 525 + 
        (currentPlayer.getMaxHealth()-currentPlayer.getHealth())*(195/currentPlayer.getMaxHealth()), 185,
        currentPlayer.getHealth()*(195/currentPlayer.getMaxHealth()));
        //kitöltsük kékre a játékos energiáját jelző gömböt
        g.setColor(Color.blue);
        g.fillRect(832, 525 + (4 - currentPlayer.getEnergy())*(195/4), 197, currentPlayer.getEnergy()*(195/4));
        
        //kirajzoljuk az Inventory hátterét
        g.drawImage(backGround, 0, 0, p);

        //végig megyünk a játékos eszközein/tárgyain, mindegyiket megjelenítsük az eszköztárban
        for (int i = 0; i < currentPlayer.getItems().size(); ++i){
            //megpróbáljuk betölteni az eszköz képét
            try {
                BufferedImage temp = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\"+currentPlayer.getItems().get(i).getName()+".png"));
                //beállítjuk az eszköz gombjának actionCommand-jét
                itemButtons.get(i).setActionCommand("item " + currentPlayer.getItems().get(i).getName());
                //kirajzoljuk a betöltött képet
                g.drawImage(temp, 417 + i*80, 651, p);

                //ha törékeny ásó van nála, akkor kiírjunk annak az élettartamát
                if (currentPlayer.getItems().get(i).getName().equals("spade"))
                {
          			g.setFont(new Font("Arial", Font.BOLD, 12));
             		g.setColor(Color.white);
             		Spade s = (Spade)(currentPlayer.getItems().get(i));
             		String w = String.valueOf(s.getHealth());
             		g.drawString(w, 417 + i*80 + 5, 651+55);
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    /**
	 * A currentPlayer gettere
     * @return Player
 	 */
    public Player getCurrentPlayer(){ return currentPlayer;}
    /**
	 * A currentPlayer settere
     * @param player Player
 	 */
    public void setCurrentPlayer(Player player){currentPlayer = player;}

    /**
	 * Az itemButtons gettere
     * @return ArrayList<JButton>
 	 */
    public ArrayList<JButton> getItemButtons(){return itemButtons; }
    /**
	 * Az itemButtons settere
     * @param buttons ArrayList<JButton>
 	 */
    public void setItemButtons(ArrayList<JButton> buttons){ itemButtons = buttons;}

    /**
	 * Az endTurnButton gettere
     * @return JButton
 	 */
    public JButton getEndTurnButton(){return endTurnButton;}
    /**
	 * Az endTurnButton settere
     * @param button JButton
 	 */
    public void setEndTurnButton(JButton button){endTurnButton = button;}


}