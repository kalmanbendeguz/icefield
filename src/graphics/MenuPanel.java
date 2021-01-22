package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A menüt megvalósító panel
 *
 */
public class MenuPanel extends JPanel {
	/**
	 * A panel háttere
	 */
    private BufferedImage backGround = new BufferedImage(1200, 720, BufferedImage.TYPE_INT_RGB);
    /**
     * A menügombok
     */
    private ArrayList<JButton> menuButtons = new ArrayList<JButton>();
    /**
     * A konstruktor
     */
    public MenuPanel() {
    	this.setLayout(null);
		this.setBounds(0,0, 1200, 720);
        try
        {
            backGround = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\menu.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //gombok letrehozasa
        JButton newGame = new JButton();
        JButton settings = new JButton();
        JButton bestScores = new JButton();
        JButton exit = new JButton();

        /*
        final JTextField textbox = new JTextField();
        textbox.setBounds(50, 50, 150, 20);
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textbox.setText("NewGame"); // debug

                newGame();
            }
        });
        */

        //gombok felparameterezese
        newGame.setBounds(370, 267, 573, 75);
        newGame.setOpaque(false);
        newGame.setContentAreaFilled(false);
        newGame.setBorderPainted(false);
        newGame.setActionCommand("newgame");

        settings.setBounds(370, 363, 573, 75);
        settings.setOpaque(false);
        settings.setContentAreaFilled(false);
        settings.setBorderPainted(false);
        settings.setActionCommand("options");

        bestScores.setBounds(370, 467, 573, 75);
        bestScores.setOpaque(false);
        bestScores.setContentAreaFilled(false);
        bestScores.setBorderPainted(false);
        bestScores.setActionCommand("highscores");

        exit.setBounds(370, 567, 573, 75);
        exit.setOpaque(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setActionCommand("exit");

        //gombok listahoz valo hozzaadasa
        menuButtons.add(newGame);
        menuButtons.add(settings);
        menuButtons.add(bestScores);
        menuButtons.add(exit);

        this.add(newGame);
        this.add(settings);
        this.add(bestScores);
        this.add(exit);
	    this.setVisible(true);
	    this.setFocusable(true);
    }
    /**
     * Kirajzolja a panelt
     */
    @Override
    public void paint(Graphics g)
    {
        g.drawImage(backGround, 0,0, this);
    }

   /**
    * A háttér gettere
    * @return háttérkép
    */
    public BufferedImage getBackGround() {
        return backGround;
    }
    /**
     * A háttér settere
     * @param backGround háttérkép
     */
    public void setBackGround(BufferedImage backGround) {
        this.backGround = backGround;
    }
    /**
     * A menügombok gettere
     * @return menügombok
     */
    public ArrayList<JButton> getMenuButtons() {
        return menuButtons;
    }
    /**
     * A menügombok settere
     * @param menuButtons menügombok
     */
    public void setMenuButtons(ArrayList<JButton> menuButtons) {
        this.menuButtons = menuButtons;
    }
}