package polutionMan;

import javax.swing.JFrame;

public class Tela {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Polution Man - Missão Limpeza");

		Game gamePanel = new Game();
		window.add(gamePanel);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.startGameThread();
	}
}