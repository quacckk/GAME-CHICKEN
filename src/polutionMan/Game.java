package polutionMan;

import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{

	Thread gameThread;
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		//ISSO VAI CRIAR O NOSSO LOOP NO JOGO
		//UMA DAS COISAS IMPORTANTES PARA O JOGO
		while(gameThread !=null) {
			//UPDATE: atualiza a informação da posição do personagem
			
			//DRAW: desenha na tela a informação atualizada
		}
		
	}	
}
