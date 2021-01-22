package graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import icefield.Controller;
/**
 * A játékpanelt megvalósító osztály
 *
 */
public class GamePanel extends JPanel{
	/**
	 * A panel háttere
	 */
    private BufferedImage backGround;
    /**
     * A pálya háttere
     */
    private BufferedImage map;
    /**
     * Az eszköztár
     */
    private Inventory inventory = null;
    /**
     * A fieldek képei
     */
    private ArrayList<FieldPanel> fields = new ArrayList<FieldPanel>();
    /**
     * A controller példánya
     */
    private Controller controller;
    /**
     * Konstruktor
     * @param palya jelenlegi pálya
     * @param c controller
     */
    public GamePanel(String palya, Controller c)
    {
		this.setLayout(null);
		this.setBounds(0,0, 1200, 720);
	    try
	    {
	        backGround = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\background.png"));
	        map = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\"+palya+".png"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    controller = c;
	    createFields(palya);
	    inventory = new Inventory(controller.getCurrentPlayer());
	    this.add(inventory.getEndTurnButton());
	    for(JButton b : inventory.getItemButtons())
	    	this.add(b);
	    this.setVisible(true);
	    this.setFocusable(true);
    }
    /**
     * Kirajzolja a panelt
     */
    @Override
    public void paint(Graphics g)
    {
    	refreshInventory();
    	//1. háttér
        g.drawImage(backGround, 0,0, this); 
        //2. map
        g.drawImage(map, 0,0, this); 
        //3. fieldek
        for(FieldPanel f : fields)
        	f.paint(g, this, controller.getCurrentPlayer());
        
        //4. inventory
        inventory.paint(g, this);

    }
    /**
     * Inventory gettere
     * @return
     */
    public Inventory getInventory()
    {
    	refreshInventory();
    	return inventory;
    }
    /**
     * Frissíti az inventoryt
     */
    public void refreshInventory()
    {
    	inventory.setCurrentPlayer(controller.getCurrentPlayer());
    }
    /**
     * Létrehozza a fieldeket a kiválasztott pálya alapján
     * @param palya a kiválasztott pálya neve
     */
    public void createFields(String palya)
    {
    	fields.clear();
    	// poziciok bellítasa
    	switch(palya)
    	{
    	case "foci":
    		fields.add(new FieldPanel(controller.getFields().get(0),108,191,  144,99, 0));
    		fields.add(new FieldPanel(controller.getFields().get(1),246,61,  183,120, 1));
    		fields.add(new FieldPanel(controller.getFields().get(2),514,135,  150,70, 2));
    		fields.add(new FieldPanel(controller.getFields().get(3),294,201,  140,105, 3));
    		fields.add(new FieldPanel(controller.getFields().get(4),472,224,  247,73, 4));
    		fields.add(new FieldPanel(controller.getFields().get(5),793,162,  160,152, 5));
    		fields.add(new FieldPanel(controller.getFields().get(6),971,282,  166,148, 6));
    		fields.add(new FieldPanel(controller.getFields().get(7),140,320, 180,120, 7));
    		fields.add(new FieldPanel(controller.getFields().get(8),397,316,  116,87, 8));
    		fields.add(new FieldPanel(controller.getFields().get(9),585,316,  201,83, 9));
    		fields.add(new FieldPanel(controller.getFields().get(10),358,427,  134,97, 10));
    		fields.add(new FieldPanel(controller.getFields().get(11),542,408,  184,146, 11));
    		fields.add(new FieldPanel(controller.getFields().get(12),796,385,  150,93, 12));
    		break;
    	case "nagy":
    		fields.add(new FieldPanel(controller.getFields().get(0),203,74,  165,74, 0));
    		fields.add(new FieldPanel(controller.getFields().get(1),416,79,  186,74, 1));
    		fields.add(new FieldPanel(controller.getFields().get(2),632,62,  237,101, 2));
    		fields.add(new FieldPanel(controller.getFields().get(3),906,88,  168,70, 3));
    		fields.add(new FieldPanel(controller.getFields().get(4),296,154,  168,80, 4));
    		fields.add(new FieldPanel(controller.getFields().get(5),497,170,  211,68, 5));
    		fields.add(new FieldPanel(controller.getFields().get(6),730,180,  202,72, 6));
    		fields.add(new FieldPanel(controller.getFields().get(7),120,163,  154,134, 7));
    		fields.add(new FieldPanel(controller.getFields().get(8),281,243,  177,60, 8));
    		fields.add(new FieldPanel(controller.getFields().get(9),467,241,  108,116, 9));
    		fields.add(new FieldPanel(controller.getFields().get(10),810,265,  162,66, 10));
    		fields.add(new FieldPanel(controller.getFields().get(11),985,195,  175,105, 11));
    		fields.add(new FieldPanel(controller.getFields().get(12),28,300,  106,163, 12));
    		fields.add(new FieldPanel(controller.getFields().get(13),601,292,  190,67, 13));
    		fields.add(new FieldPanel(controller.getFields().get(14),872,346,  188,76, 14));
    		fields.add(new FieldPanel(controller.getFields().get(15),144,424,  167,82, 15));
    		fields.add(new FieldPanel(controller.getFields().get(16),345,440,  187,82, 16));
    		fields.add(new FieldPanel(controller.getFields().get(17),548,363,  97,94, 17));
    		fields.add(new FieldPanel(controller.getFields().get(18),710,367,  135,71, 18));
    		fields.add(new FieldPanel(controller.getFields().get(19),1065,363,  104,126,19));
    		fields.add(new FieldPanel(controller.getFields().get(20),659,441,  133,115, 20));
    		fields.add(new FieldPanel(controller.getFields().get(21),856,431,  157,65, 21));
    		fields.add(new FieldPanel(controller.getFields().get(22),274,312,  134,103, 22));
    		break;
    	case "teszt":
    		fields.add(new FieldPanel(controller.getFields().get(0),108,191,  144,99, 0));
    		fields.add(new FieldPanel(controller.getFields().get(1),246,61,  183,120, 1));
    		fields.add(new FieldPanel(controller.getFields().get(2),514,135,  150,70, 2));
    		fields.add(new FieldPanel(controller.getFields().get(3),294,201,  140,105, 3));
    		fields.add(new FieldPanel(controller.getFields().get(4),472,224,  247,73, 4));
    		fields.add(new FieldPanel(controller.getFields().get(5),793,162,  160,152, 5));
    		fields.add(new FieldPanel(controller.getFields().get(6),971,282,  166,148, 6));
    		fields.add(new FieldPanel(controller.getFields().get(7),140,320, 180,120, 7));
    		fields.add(new FieldPanel(controller.getFields().get(8),397,316,  116,87, 8));
    		fields.add(new FieldPanel(controller.getFields().get(9),585,316,  201,83, 9));
    		fields.add(new FieldPanel(controller.getFields().get(10),358,427,  134,97, 10));
    		fields.add(new FieldPanel(controller.getFields().get(11),542,408,  184,146, 11));
    		fields.add(new FieldPanel(controller.getFields().get(12),796,385,  150,93, 12));
    		break;
    	}
    	// buttonok hozzaadasa
    	for(FieldPanel f: fields)
    		this.add(f.getButton());
    }
    /**
     * A feildek gettere
     * @return
     */
    public ArrayList<FieldPanel> getFields(){return fields;}
}
