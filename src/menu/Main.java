package menu;

import graphics.Container;
import graphics.MenuPanel;

import javax.swing.*;

import java.io.InputStream;
import java.util.Scanner;
/**
 * A fő osztály
 *
 */
public class Main {
	public static Object lock = new Object();
/**
 * A program main függvénye
 * @param args
 */
	public static void main(String[] args) {
		Menu m = new Menu();
		Container c= new Container(m);
		boolean game = true;
		while (game)
		{
			m.ShowMenuItems();
			
			
			synchronized(lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Scanner in = new Scanner(System.in);
			
			try {
				MenuItem n=	MenuItem.values()[in.nextInt()-1];
				game = m.ChooseMenuItem(n);
			}
			catch(Exception ex){
				System.out.println("Invalid input.");
			}
		}
	}
}
