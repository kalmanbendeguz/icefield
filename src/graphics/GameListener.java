package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import field.Field;
import icefield.Controller;
import menu.Main;
import menu.Menu;
import menu.Options;

public class GameListener implements ActionListener, KeyListener, MouseListener {
	
	/**
	 * A játékprogram főablaka, ezt kell mindig újrarajzolni a modell módosítása után
	 */
	private Container container;
	
	/**
	 * A modell eléréséhez szükséges, hogy tudjunk rajta módosításokat végrehajtani
	 */
	private Controller controller;
	
	/**
	 * A modellhez tartozó menü, hogy a beállításokat állíthassuk
	 */
	private Menu menu;
	
	/**
	 * A standard kimenet
	 */
	private PrintStream stdout;
	
	/**
	 * A standard bemenet
	 */
	private InputStream stdin;
	
	/**
	 * A játék aktuális paneljét (menü, pályaválasztás, beállítások, játék, stb..) azonosító szöveg
	 */
	private String currentPanel;
	
	/**
	 * Van olyan view-től érkező üzenet, amikor a modellnek nem kell parancsot küldeni, ezzel tudjuk ezt jelezni
	 */
	private boolean sendCommand = true;
	
	/**
	 * Konstruktor, elmenti a standard be- és kimenetet, mivel át fogjuk állítani
	 * @param _container A játék főablaka, ezt kell mindig újrarajzolni a modell módosítása után
	 * @param _menu A modellhez tartozó menü, hogy a beállításokat állíthassuk
	 * @throws UnsupportedEncodingException Akkor dobódik, ha a ByteArrayInputStream nem jó kódolás-azonosító stringet kap
	 * @throws InterruptedException
	 */
	public GameListener(Container _container, Menu _menu) throws UnsupportedEncodingException, InterruptedException {
		this.container = _container;
		this.menu = _menu;
		this.controller = menu.getController();
		stdout = System.out;
		stdin = System.in;
		String cmd = "";
		currentPanel = "menu";
		System.setIn(new ByteArrayInputStream(cmd.getBytes()));
	}
	
	/**
	 * A View-től érkező, gombokra és panelekre történő bal kattintásokat lekezelő függvény
	 * @param e Az érkezett esemény
	 */
	@Override
	public void actionPerformed(ActionEvent e) { 
		String[] cmd_from_view = e.getActionCommand().split("\\s+");
		//System.out.println("command from view: ["+e.getActionCommand()+"]"); //debug célból
		String cmd_to_model = "";
		
		switch(cmd_from_view[0]) {
			case "newgame":{
				cmd_to_model = "1";
				container.navigate("choosemap");
				currentPanel = "choosemap";
			} break;
			case "options":{
				cmd_to_model = "2";
				container.navigate("options");
				currentPanel = "options";
			} break;
			case "highscores":{
				cmd_to_model = "3";
				container.navigate("highscores");
				currentPanel = "highscores";
			} break;
			case "exit":{
				cmd_to_model = "4";
				container.dispose();
			} break;
			case "submit":{

				String newName = "";
				for(int i = 1;i < cmd_from_view.length; ++i) {
					newName += cmd_from_view[i] + " ";
				}
				cmd_to_model = "1";
				try {
					sendCommandToModel(cmd_to_model);
				} catch (UnsupportedEncodingException | InterruptedException e1) {
					e1.printStackTrace();
				}
				synchronized(Options.nameIsReadyToSet) {
					try {
						Options.nameIsReadyToSet.wait();
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
				cmd_to_model = newName;
				
			} break;
			case "togglemusic":{
				boolean music = menu.getOptions().GetMusic();
				cmd_to_model = "2";
				try {
					sendCommandToModel(cmd_to_model);
				} catch (UnsupportedEncodingException | InterruptedException e1) {
					e1.printStackTrace();
				}
				synchronized(Options.musicIsReadyToSet) {
					try {
						Options.musicIsReadyToSet.wait();
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
				if(music) {
					cmd_to_model = "off";
				}else {
					cmd_to_model = "on";
				}
			} break;
			case "remove_snow":{
				cmd_to_model = "remove snow";
			}break;
			case "build_igloo":{
				cmd_to_model = "build igloo";
			}break;
			case "pick_up_item":{
				cmd_to_model = "pick up item";
			}break;
			case "menu":{
				cmd_to_model = "menu";
			}break;
			case "endturn": {
				cmd_to_model = "end turn";
				if(controller.TryFireWithoutWaiting() || controller.checkIfGameLost()) {
					if(controller.checkIfGameLost()) {
						container.navigate("lose");
						currentPanel = "lose";
					} else if(controller.getWon()) {
						container.navigate("win");
						currentPanel = "win";
					} 
				}
			}break;
			case "nagy": {
				try {
					sendCommandToModel("nagypalya");
					synchronized(Controller.mapLoaded) {
						Controller.mapLoaded.wait();
					}
					sendCommandToModel("N");
					
					synchronized(Controller.mapLoaded) {
						Controller.mapLoaded.wait();
					}
				} catch (Exception e1) {}
				container.navigate("nagy");
				currentPanel = "nagy";
				sendCommand = false;
			}break;
			case "foci": {
				try {
					sendCommandToModel("focilabda");
					synchronized(Controller.mapLoaded) {
						Controller.mapLoaded.wait();
					}
					sendCommandToModel("N");
					
					synchronized(Controller.mapLoaded) {
						Controller.mapLoaded.wait();
					}
				} catch (Exception e1) {}
				container.navigate("foci");
				currentPanel = "foci";
				sendCommand = false;
			}break;
			case "teszt": {
				try {
					sendCommandToModel("tesztpalya");
					while(controller.getPlayers().size() == 0) {
						try {
							synchronized(Controller.readyForTestCommand) {
								Controller.readyForTestCommand.wait();
							}
							JFrame noPlayers = new JFrame("No players on map");
							String cmd_from_testframe = JOptionPane.showInputDialog(noPlayers, "Please put down a player:");
							sendCommandToModel(cmd_from_testframe);
							synchronized(Controller.commandDone) {
								Controller.commandDone.wait();
							}
						} catch (Exception e3) {}			
					}
					synchronized(Controller.mapLoaded) {
						sendCommandToModel("Y");
						Controller.mapLoaded.wait();
					}
					
					String input_test = "";
					int count = 0;
					do {
						try {
							if(count != 0) {
								synchronized(Controller.readyForTestCommand) {
									Controller.readyForTestCommand.wait();
						        }
								count = 1;
							}
							container.repaint();
							JFrame testCommands = new JFrame("Test commands");
							input_test = JOptionPane.showInputDialog(testCommands, "Type test commands and start the game with \"start\".\nGame will automatically start in TEST mode.");
							System.out.println("command from window: ["+input_test+"]");
							sendCommandToModel(input_test);
						} catch(Exception e3) {}
						
					} while (!input_test.contentEquals("start"));
					
				} catch (Exception e1) {}
				container.navigate("teszt");
				currentPanel = "teszt";
				sendCommand = false;
			} break;
			case "field": {
				int globalIndex = Integer.parseInt(cmd_from_view[1]);
				if(globalIndex == controller.getFields().indexOf(controller.getCurrentPlayer().getCurrentField())) {
					
					if(controller.getFields().get(globalIndex).getSnow() == 0) {
						cmd_to_model = "pick up item";
					} else {
						cmd_to_model = "remove snow";
					}
				} else {
					Field moveTo = controller.getFields().get(globalIndex);
					int direction = controller.getCurrentPlayer().getCurrentField().getNeighbors().indexOf(moveTo);
					int dirToModel = direction + 1;
					cmd_to_model = "move " + dirToModel; 
				}
			} break;
			case "item": {
				String item = cmd_from_view[1];
				cmd_to_model = "use "+ item;
			}break;
			default: {} break;
		}
		
		try {
			if(sendCommand) {
				sendCommandToModel(cmd_to_model);
			} else {
				sendCommand = true;
			}	
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		container.repaint();
	}
	
	/**
	 * A View-től érkező, billentyűlenyomásokra reagáló függvény
	 * @param e A billentyűlenyomás eseménye
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		String cmd_to_model = "";
		if(currentPanel.contentEquals("win") || currentPanel.contentEquals("lose")) {
			cmd_to_model = "x";
			container.navigate("menu");
			currentPanel = "menu";
		} else if (key == KeyEvent.VK_T) {
			JFrame noPlayers = new JFrame("Test command");
			cmd_to_model = JOptionPane.showInputDialog(noPlayers, "Type test command:");
		}
		
		if(key == KeyEvent.VK_ESCAPE) {
			switch(currentPanel) {
			case "menu": break;
			case "choosemap": break;
			
			case "options":{
				cmd_to_model = "3";
				container.navigate("menu");
				currentPanel = "menu";
			} break;
			case "highscores": {
				cmd_to_model = "1";
				container.navigate("menu");
				currentPanel = "menu";
			} break;
			case "teszt": {
				cmd_to_model = "menu"+System.lineSeparator();
				container.navigate("menu");
				currentPanel = "menu";
			} break;
			case "foci": {
				cmd_to_model = "menu"+System.lineSeparator();
				container.navigate("menu");
				currentPanel = "menu";
			} break;
			case "nagy": {
				cmd_to_model = "menu"+System.lineSeparator();
				container.navigate("menu");
				currentPanel = "menu";
			} break;
			default: break;
			}
		}
		try {
			sendCommandToModel(cmd_to_model);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		container.repaint();
	}
	
	/**
	 * A View-től érkező, gombokra és panelekre történő jobb kattintásokat lekezelő függvény
	 * @param e Az érkezett esemény
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		JButton j = new JButton();
		try {
			j = (JButton) e.getComponent();
		} catch(Exception ex) {}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			String[] cmd_from_view = j.getActionCommand().split("\\s+");
			String cmd_to_model = "";
			
			switch(controller.getCurrentPlayer().getName().toUpperCase()) {
				case "SCIENTIST":{
					int globalIndex = Integer.parseInt(cmd_from_view[1]);
					Field toInspect = controller.getFields().get(globalIndex);
					int direction = controller.getCurrentPlayer().getCurrentField().getNeighbors().indexOf(toInspect);
					int dirToModel = direction + 1;
					cmd_to_model = "inspect " + dirToModel;
				} break;
				case "ESKIMO":{
					cmd_to_model = "igloo";
				} break;
				default: break;
			}
			try {
				sendCommandToModel(cmd_to_model);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			container.repaint();
		}	
	}
	
	/**
	 * Interfész delegálásához szükséges
	 * @param e Az érkezett esemény
	 */
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	/**
	 * Interfész delegálásához szükséges
	 * @param e Az érkezett esemény
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	/**
	 * Interfész delegálásához szükséges
	 * @param e Az érkezett esemény
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
	
	/**
	 * Interfész delegálásához szükséges
	 * @param e Az érkezett esemény
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * Interfész delegálásához szükséges
	 * @param e Az érkezett esemény
	 */
	@Override
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * Interfész delegálásához szükséges
	 * @param e Az érkezett esemény
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	/**
	 * A modellnek a parancsokat küldő függvény, amelyek a háttérben futó játéklogikát vezérlik
	 * @param cmd A küldött parancs
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
	private void sendCommandToModel(String cmd) throws UnsupportedEncodingException, InterruptedException {
		cmd = cmd + System.lineSeparator();
		System.setIn(new ByteArrayInputStream(cmd.getBytes("UTF-8")));
		synchronized(Main.lock) {
			Main.lock.notifyAll();
		}
		//stdout.println("command to model: ["+cmd+"]"); //debug célból
	}
}
