package polutionMan;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{
	
	
	//FPS DO JOGO
	int FPS = 60;
	
	ControleTeclado keyH = new ControleTeclado();
	Thread gameThread;
	
	int speedPlayer = 4;
	//posição de origem do jogador
	int playerX = 0;
	int playerY = 0;
	
	public Game() {
		
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		//ISSO VAI CRIAR O NOSSO LOOP NO JOGO
		//UMA DAS COISAS IMPORTANTES PARA O JOGO
		
		//AGORA VOU CRIAR O LOOP
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread !=null) {
			//UPDATE: atualiza a informação da posição do personagem
			
			currentTime = System.nanoTime();			
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if(delta >=1) {
				update();
				
				
				//DRAW: desenha na tela a informação atualizada
				repaint();
				delta--;
				
			}
			
			
		}
		
	}	
	public void update() {
		
		if(keyH.upPressed == true) {
			playerY -= speedPlayer;
		}
		else if(keyH.downPressed == true) {
			playerY += speedPlayer;
		}
		else if(keyH.rightPressed == true) {
			playerX += speedPlayer;
		}
		else if(keyH.leftPressed == true) {
			playerX -= speedPlayer;
		}
	}
	//possivelmente os lugares que vão estar o sprites
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	}
	
}
