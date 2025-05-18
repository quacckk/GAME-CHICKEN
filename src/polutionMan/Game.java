package polutionMan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{
	
	
	//FPS DO JOGO
	int FPS = 60;
	
	ControleTeclado keyH = new ControleTeclado();
	Thread gameThread;
	personagem personagem;
	item item;
	boolean jogoRodando = false;
	boolean personagemTemVassoura = false;
	
	int speedPlayer = 4;
	//posição de origem do jogador
	int playerX = 0;
	int playerY = 0;
	
	public Game() {
		
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	public void setupGame() {
		int yPersonagemInicial = ALTURA_JANELA - (ALTURA_TILE * 2);
		int yLimiteVassoura = ALTURA_TILE;

		personagem = new Personagem(LARGURA_JANELA / 2 - LARGURA_TILE / 2, yPersonagemInicial, LARGURA_TILE,
				ALTURA_TILE, 4, yLimiteItem, LARGURA_JANELA, ALTURA_JANELA);

		item = new item(LARGURA_TILE, ALTURA_TILE);

		Inimigo = new ArrayList<>();
		inicializarInimigo();

		personagemTemItem = false;
		item.setAtiva(false);
		jogoRodando = true;
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
		
		if (!personagemTemItem && personagem.chegouAoLocalDaItem()) {
			personagemTemItem = true;
			item.setAtiva(true);
			System.out.println("Personagem pegou a vassoura!");
		}

		if (personagemTemItem) {
			item.update(personagem);
			verificarColisaoVassouraComLixo();
		} else {
			verificarColisaoPersonagemComLixo();
		}

		for (Inimigo lixo : Inimigo) {
			if (lixo.isVisivel()) {
				// lixo.update();
			}
		}
	}
	
	public void verificarColisaoPersonagemComLixo() {
		Rectangle boundsPersonagem = personagem.getBounds();
		for (Inimigo lixo : Inimigo) {
			if (lixo.isVisivel() && boundsPersonagem.intersects(lixo.getBounds())) {
				System.out.println("GAME OVER: Personagem colidiu com lixo!");
				jogoRodando = false;
				return;
			}
		}
	}
	
	public void verificarColisaoVassouraComLixo() {
		if (!item.isAtiva()) {
			return;
		}

		Rectangle boundsVassoura = item.getBounds();
		Iterator<Inimigo> iterator = Inimigo.iterator();

		while (iterator.hasNext()) {
			Inimigo lixo = iterator.next();
			if (lixo.isVisivel() && boundsVassoura.intersects(lixo.getBounds())) {
				System.out.println("Vassoura limpou um lixo!");
				lixo.setVisivel(false);
			}
		}
	}
	
	
	//possivelmente os lugares que vão estar o sprites
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	}
	
}
