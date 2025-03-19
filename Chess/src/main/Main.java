package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("Simple Chess");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		//Adding the GamePanel to the window JFrame
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack(); //by packing it, the window will adjust its size to the game panel
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gp.launchGame();


	}

}
