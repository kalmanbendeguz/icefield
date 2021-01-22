package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * A beállításokat megvalósító panel
 *
 */
public class OptionsPanel extends JPanel
{
	/**
	 * A háttérkép
	 */
    private BufferedImage backGround;
    /**
     * A játékos nevét tartalmazó textfield
     */
    private JTextField nameTextField;
    /**
     * A zene állapotát beálltó checkbox
     */
    private JCheckBox music;
    /**
     * Az új név mentésére szolgáló gomb
     */
    private JButton submitButton;


    /**
     * Konstuktor
     * @param music igaz ha van zene optionsbe lépéskor
     */
    public OptionsPanel(boolean m)
    {
        this.setLayout(null);
		this.setBounds(0,0, 1200, 720);
        try
        {
            backGround = ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\images\\options.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Font AmaticSc = new Font("Amatic sc", Font.PLAIN, 72);
        try {
			AmaticSc = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir")+"\\src\\images\\amaticsc.ttf")).deriveFont(72f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Nametextfield beallitasa
        nameTextField = new JTextField();
        nameTextField.setBounds(270,360,240,60);
        nameTextField.setVisible(true);
        nameTextField.setFont(AmaticSc);
        nameTextField.setForeground(new Color(198, 205, 229));
        nameTextField.setText("Player");
        nameTextField.setBackground(new Color(26, 39, 79));
        nameTextField.setBorder(new LineBorder(new Color(26, 39, 79),1));
        
      //SubmitButton felparameterezese
        submitButton = new JButton();
        submitButton.setBounds(599, 328, 300, 120);
        submitButton.setOpaque(false);
        submitButton.setContentAreaFilled(false);
        submitButton.setBorderPainted(false);
        submitButton.setActionCommand("submit Player");
        this.add(submitButton);
        
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) {
        		  update();
        	  }
        	  public void removeUpdate(DocumentEvent e) {
        		  update();
        	  }
        	  public void insertUpdate(DocumentEvent e) {
        		  update();

        	  }

        	  public void update() {
        		  String s = nameTextField.getText();
        	     if (s.length()<1)
        	     {
        	       s = "Player";
        	     }
        	     else if (s.length()>10)
        	     {
        	    	 s = s.substring(0,10);
          	     }
        	     submitButton.setActionCommand("submit "+s);
        	  }
        	});
        
        this.add(nameTextField);
       
        //Music CheckBox felparameterezese
        music = new JCheckBox();
        music.setBounds(695, 500, 104, 100);
        music.setBackground(new Color(26, 39, 79));
        music.setIcon(new ImageIcon("src\\images\\buttonon.png"));
        music.setSelectedIcon(new ImageIcon("src\\images\\buttonoff.png"));
        music.setActionCommand("togglemusic");
        music.setVisible(true);
        music.setSelected(!m);
        this.add(music);
	    this.setVisible(true);
	    this.setFocusable(true);
    }
    /**
     * Kirajzolja a panelt
     */
    @Override
    public void paint(Graphics g)
    {
    	super.paint(g);
        g.drawImage(backGround, 0,0, this);
        nameTextField.repaint();
        music.repaint();

    }

    /**
     * A háttér gettere
     * @return a háttérkép
     */
    public BufferedImage getBackGround() {
        return backGround;
    }
    /**
     * A háttér settere
     * @param backGround a háttérkép
     */
    public void setBackGround(BufferedImage backGround) {
        this.backGround = backGround;
    }
    /**
     * A textfield gettere
     * @return textfield
     */
    public JTextField getNameTextField() {
        return nameTextField;
    }
    /**
     * A textfield settere
     * @param nameTextField textfield
     */
    public void setNameTextField(JTextField nameTextField) {
        this.nameTextField = nameTextField;
    }
    /**
     * A checkbox gettere
     * @return checkbox
     */
    public JCheckBox getMusic() {
        return music;
    }
    /**
     * A checkbox settere
     * @param music checkbox
     */
    public void setMusic(JCheckBox music) {
        this.music = music;
    }
    /**
     * Az küldés gomb gettere
     * @return a küldés gomb
     */
    public JButton getSubmitButton() {
        return submitButton;
    }
    /**
     * A küldés gomb settere
     * @param submitButton a küldésgomb
     */
    public void setSubmitButton(JButton submitButton) {
        this.submitButton = submitButton;
    }
}
