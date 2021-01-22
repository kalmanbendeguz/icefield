package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * A játék pályáját kiválasztó panel 
 *
 */
public class MapPanel extends JPanel{
	/**
	 * A panel háttere
	 */
    private BufferedImage backGround;
    /**
     * A pályák gombjai
     */
    private ArrayList<JButton> mapButtons = new ArrayList<JButton>();
    /**
     * Konstruktor
     */
    public MapPanel()
    {
    	this.setLayout(null);
		this.setBounds(0,0, 1200, 720);
	    try
	    {
	        backGround = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\map.png"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    JButton foci = new JButton();
	    JButton nagy = new JButton();
	    JButton teszt = new JButton();
	    
	    //gombok felparameterezese
        foci.setBounds(56, 46, 495, 294);
        foci.setOpaque(false);
        foci.setContentAreaFilled(false);
        foci.setBorderPainted(false);
        foci.setActionCommand("foci");

        nagy.setBounds(639, 44, 495, 294);
        nagy.setOpaque(false);
        nagy.setContentAreaFilled(false);
        nagy.setBorderPainted(false);
        nagy.setActionCommand("nagy");

        teszt.setBounds(353, 377, 495, 294);
        teszt.setOpaque(false);
        teszt.setContentAreaFilled(false);
        teszt.setBorderPainted(false);
        teszt.setActionCommand("teszt");

        //gombok listahoz valo hozzaadasa
        mapButtons.add(foci);
        mapButtons.add(nagy);
        mapButtons.add(teszt);
        
        this.add(foci);
        this.add(nagy);
        this.add(teszt);
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
     * A gombok gettere
     * @return
     */
    public ArrayList<JButton> getMapButtons() {
        return mapButtons;
    }
}
