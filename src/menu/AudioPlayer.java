package menu;
import java.io.File;
import java.io.FileOutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * A zenelejátszó osztálya
 *
 */
public class AudioPlayer  {

	/**
	 * A lejátszáshoz szükséges clip osztály
	 */
	private Clip audioClip;
	/**
	 * megadja, hogy az audió éppen lejátszódik-e vagy sem
	 */
	private boolean playing=false;

		/**Elindítja a zenét
		 * @param menu megadja, hogy a menü zenéjét játszuk-e le vagy sem
		 */
	    void Start(boolean menu) {
	    	System.out.println("Audio is playing.");
	    	playing = true;
	    	String filename = menu? "menu":"main";
	        File f = new File(System.getProperty("user.dir")+"\\src\\menu\\"+filename+".wav");
	        AudioInputStream audioInputStream;
			try {
				audioInputStream = AudioSystem.getAudioInputStream(f);
		        audioClip = AudioSystem.getClip();
		        audioClip.open(audioInputStream);
		        audioClip.loop(audioClip.LOOP_CONTINUOUSLY);
			} catch (Exception e) {
				System.out.println("Audio error.");
			}
         
	    }
	    /**
	     * Leállítja a zenét
	     */
	    public void Stop()
	    {
	    	audioClip.stop();
	    	System.out.println("Audio stopped.");
	    	playing = false;
	    }
	    /**
	     * A zene lejátszását frissítő függvény
	     * @param music megadja, hogy legyen-e zene vagy sem
	     */
	    public void Reset(boolean music)
	    {
	    	if (!music && playing)
	    		Stop();
	    	else if(music && !playing)
	    		Start(true);
	    }
}
