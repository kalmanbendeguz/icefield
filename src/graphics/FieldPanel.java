package graphics;

import java.util.ArrayList;
import java.util.Stack;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math; 

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import field.Field;
import item.Item;
import player.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
/**
 * A fieldeket megvalósító panel
 *
 */
public class FieldPanel{
	/**
	 * A playerek képei
	 */
    private ArrayList<BufferedImage> playerImages;
    /**
     * Az eszközök képei
     */
    private Stack<BufferedImage> itemImages;
    /**
     * A jegesmedve képe
     */
    private BufferedImage polarbearImage;
    /**
     * A sátor, iglu vagy lyuk képe
     */
    private BufferedImage thingImage;
    /** 
     * A hó képe
     */
    private BufferedImage snowImage;
    /**
     * A fieldgomb
     */
    private JButton button;
    /**
     * A modellbeli field
     */
    private Field field;
    /**
     * A feild x koordinátája a képernyőn
     */
    private int posX;
    /**
     * A feild y koordinátája a képernyőn
     */
    private int posY;
    /**
     * A feild szélessége a képernyőn
     */
    private int width;
    /**
     * A feild magassága a képernyőn
     */
    private int height;
    /**
     * A player képeinek gettere
     * @return player képei
     */
    public ArrayList<BufferedImage> getPlayerImages(){ return playerImages; }
    /**
     * A player képeinek settere
     * @param value a player képei
     */
    public void setPlayerImages(ArrayList<BufferedImage> value) { playerImages=value;}   
    /**
     * Az eszközök képeinek gettere
     * @return az eszközök képei
     */
    public Stack<BufferedImage> getItemImages() { return itemImages;}
    /**
     * Az eszközök képeinek settere
     * @param value az eszközök képei
     */
    public void setItemImages(Stack<BufferedImage> value) { itemImages = value;}
    /**
     * A jegesmedve képének gettere
     * @return a jegesmedve képe
     */
    public BufferedImage getPolarbearImage () { return polarbearImage;}
    /**
     * A jegesmedve képének settere
     * @param value a jegesmedve képe
     */
    public void setPolarbearImage( BufferedImage value) { polarbearImage = value;}
    /**
     * A sátor / iglu/ lyuk képének gettere
     * @return A sátor / iglu/ lyuk képe
     */
    public BufferedImage getThingImage() { return thingImage;}
    /**
     * A sátor / iglu/ lyuk képének settere
     * @param value  A sátor / iglu/ lyuk képe
     */
    public void setThingImage( BufferedImage value) { thingImage=value; }
    
    /**
     *  A hó képének gettere
     * @return a hó képe
     */
    public BufferedImage getSnowImage() {return snowImage;}
    /**
     * A hó képének settere
     * @param value a hó képe
     */
    public void setSnowImage( BufferedImage value ) {snowImage = value;} 
    /**
     *	A fieldgomb gettere
     * @return fieldgomb
     */
    public JButton getButton() {return button; }
    /**
     * A fieldgomb settere
     * @param value a fieldgomb
     */
    public void setButton( JButton value) { button = value;}
    
    /**
     * A field gettere
     * @return a field
     */
    public Field getField () {return field;}
    /**
     * A field settere
     * @param value a field
     */
    public void setField (Field value) { field = value; }
    /**
     * Konstruktor
     * @param _field afield
     * @param x a field x koordinátája
     * @param y a field y koordinátája
     * @param _width a field szélessége
     * @param _height a field magassága
     * @param num a field száma a kontrollerben
     */
    public FieldPanel (Field _field, int x, int y, int _width, int _height, int num)
    {
        posX=x;
        posY=y;
        field = _field;
        width = _width;
        height = _height;
        button = new JButton();
        button.setBounds(x,y,width, height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setActionCommand("field "+num);
        playerImages = new ArrayList<BufferedImage>();
        itemImages = new Stack<BufferedImage>();
    }
    /**
     * Kirajzolja a fieldet
     * @param g grafikus osztály példánya
     * @param p a panel amire rajzolunk
     * @param currentPlayer a jelenlegi játékos
     */
    public void paint(Graphics g, JPanel p, Player currentPlayer)
    {
    	itemImages.clear();
    	playerImages.clear();
    	polarbearImage = snowImage = thingImage = null;
    	 try
         {
             for( Item i : field.getItems())
                 itemImages.add(ImageIO.read(new File("src\\images\\"+i.getName()+".png")));

             if(field.getPolarBear() != null)
                 polarbearImage = ImageIO.read(new File("src\\images\\polarbear.png"));
             if(field.getSnow()>0)
                 snowImage = ImageIO.read(new File("src\\images\\snow.png"));
             for( Player i :field.getPlayers())
             {
            	 if (i==currentPlayer)
            		 playerImages.add( ImageIO.read(new File("src\\images\\"+i.getName()+"_selected.png")) );
            	 else
                     playerImages.add( ImageIO.read(new File("src\\images\\"+i.getName()+".png")) );
             }
             if(field.getIsUpsideDown())
             {
                 thingImage = ImageIO.read(new File("src\\images\\hole.png"));
             	playerImages.clear();

                 for( Player i :field.getPlayers())
                 {
                	 if (i==currentPlayer)
                		 playerImages.add( ImageIO.read(new File("src\\images\\"+i.getName()+"_selected_drowned.png")) );
                	 else
                         playerImages.add( ImageIO.read(new File("src\\images\\"+i.getName()+"_drowned.png")) );

                 }
             }
             else if(field.getHasIgloo())
                 thingImage = ImageIO.read(new File("src\\images\\igloo.png"));
             else if(field.getHasTent())
                 thingImage = ImageIO.read(new File("src\\images\\tent_big.png"));
         }catch(IOException e)
         {
             e.printStackTrace();
         }
         //sator/iglu
         if (thingImage!=null && field.getIsUpsideDown()==false)
        	 g.drawImage(thingImage, posX + width - 72, posY+height/2 -36, p);
         
         else if(thingImage!=null && field.getIsUpsideDown()==true)
             g.drawImage(thingImage, posX + width/2 - 72, posY + height/2 -33, p);

         //ho
         if (snowImage!=null && field.getIsUpsideDown()==false)
         g.drawImage(snowImage, posX + width/2 -63, posY + height/2 -38, p);


        //playerek
        if(playerImages.size() == 1)
        {
            g.drawImage(playerImages.get(0), posX + width/2 - 26, posY + height/2 - 112, p);  
        }
        else if(playerImages.size() > 1)
        {
            for(int i=0;i<playerImages.size();i++)
                 g.drawImage(
                    playerImages.get(i),
                    (int)(posX + width/2 - 26 + Math.cos( (-i*2*Math.PI) / playerImages.size() ) *30),
                    (int)(posY + height/2 - 112 + Math.sin ( (-i*2*Math.PI) / playerImages.size() ) *30), 
                    p);
        }
        //polarbear
        if (polarbearImage!=null)
        	g.drawImage(polarbearImage, posX+width/2-54, posY+height/2-112, p);
        
        //eszkoz
    	 if (itemImages.size()>0 && snowImage ==null && field.getIsUpsideDown()==false && field.GetIsInspected()==false)
        g.drawImage(itemImages.peek(), posX, posY, p);
    	 else if (itemImages.size()>0 &&  snowImage ==null &&field.GetIsInspected()==true)
    	 {            
    		 for(int i=0;i<itemImages.size();i++)
    			 g.drawImage(itemImages.get(i) , posX +80*i , posY, p);
    	       
    	 }
    	 //teherbiras
    	 if (field.GetIsInspected()==true)
    	 {
			//g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"\\src\\images\\amaticsc.ttf")).deriveFont(72f));
 			g.setFont(new Font("Arial", Font.BOLD, 72));
    		 g.setColor(Color.white);
    		 String w = String.valueOf(field.getMaxWeight());
    		 if (field.getMaxWeight()==10)
    			 w = String.valueOf('∞');
    			 
    		 g.drawString(w, posX + width /2,posY +height/2);
    	 }

    }


}