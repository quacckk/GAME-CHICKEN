package polutionMan;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Game extends JPanel implements Runnable {

	final int LARGURA_TILE_ORIGINAL = 16;
	final int ALTURA_TILE_ORIGINAL = 16;
	final int ESCALA = 3;

	public final int LARGURA_TILE = LARGURA_TILE_ORIGINAL * ESCALA;
	public final int ALTURA_TILE = ALTURA_TILE_ORIGINAL * ESCALA;

	public final int MAX_COLUNAS_TELA = 16;
	public final int MAX_LINHAS_TELA = 12;

	public final int LARGURA_JANELA = LARGURA_TILE * MAX_COLUNAS_TELA;
	public final int ALTURA_JANELA = ALTURA_TILE * MAX_LINHAS_TELA;

	int FPS = 60;

	Thread gameThread;
	ControleTecla controleTecla = new ControleTecla();

	Personagem personagem;
	Vassoura vassoura;
	List<Inimigo> lixos;

	boolean jogoRodando = false;
	boolean personagemTemVassoura = false;

	public Game() {
		this.setPreferredSize(new Dimension(LARGURA_JANELA, ALTURA_JANELA));
		this.setBackground(Color.decode("#87CEEB"));
		this.setDoubleBuffered(true);
		this.addKeyListener(controleTecla);
		this.setFocusable(true);
	}

	public void setupGame() {
		int yPersonagemInicial = ALTURA_JANELA - (ALTURA_TILE * 2);
		int yLimiteVassoura = ALTURA_TILE;

		personagem = new Personagem(LARGURA_JANELA / 2 - LARGURA_TILE / 2, yPersonagemInicial, LARGURA_TILE,
				ALTURA_TILE, 4, yLimiteVassoura, LARGURA_JANELA, ALTURA_JANELA);

		vassoura = new Vassoura(LARGURA_TILE, ALTURA_TILE);

		lixos = new ArrayList<>();
		inicializarLixos();

		personagemTemVassoura = false;
		vassoura.setAtiva(false);
		jogoRodando = true;
	}

	public void inicializarLixos() {
		lixos.clear();
		lixos.add(new Inimigo(LARGURA_TILE * 3, ALTURA_JANELA - (ALTURA_TILE * 4), LARGURA_TILE, ALTURA_TILE, 0));
		lixos.add(new Inimigo(LARGURA_TILE * 7, ALTURA_JANELA - (ALTURA_TILE * 4), LARGURA_TILE, ALTURA_TILE, 0));
		lixos.add(new Inimigo(LARGURA_TILE * 5, ALTURA_JANELA - (ALTURA_TILE * 6), LARGURA_TILE, ALTURA_TILE, 0));
		lixos.add(new Inimigo(LARGURA_TILE * 10, ALTURA_JANELA - (ALTURA_TILE * 6), LARGURA_TILE, ALTURA_TILE, 0));
		lixos.add(new Inimigo(LARGURA_TILE * 2, ALTURA_JANELA - (ALTURA_TILE * 8), LARGURA_TILE, ALTURA_TILE, 0));
	}

	public void startGameThread() {
		setupGame();
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000.0 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null && jogoRodando) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				drawCount = 0;
				timer = 0;
			}
		}
		mostrarGameOver(getGraphics());
	}

	public void update() {
		if (!jogoRodando)
			return;

		personagem.update(controleTecla);

		if (!personagemTemVassoura && personagem.chegouAoLocalDaVassoura()) {
			personagemTemVassoura = true;
			vassoura.setAtiva(true);
			System.out.println("Personagem pegou a vassoura!");
		}

		if (personagemTemVassoura) {
			vassoura.update(personagem);
			verificarColisaoVassouraComLixo();
		} else {
			verificarColisaoPersonagemComLixo();
		}

		for (Inimigo lixo : lixos) {
			if (lixo.isVisivel()) {
				// lixo.update();
			}
		}
	}

	public void verificarColisaoPersonagemComLixo() {
		Rectangle boundsPersonagem = personagem.getBounds();
		for (Inimigo lixo : lixos) {
			if (lixo.isVisivel() && boundsPersonagem.intersects(lixo.getBounds())) {
				System.out.println("GAME OVER: Personagem colidiu com lixo!");
				jogoRodando = false;
				return;
			}
		}
	}

	public void verificarColisaoVassouraComLixo() {
		if (!vassoura.isAtiva()) {
			return;
		}

		Rectangle boundsVassoura = vassoura.getBounds();
		Iterator<Inimigo> iterator = lixos.iterator();

		while (iterator.hasNext()) {
			Inimigo lixo = iterator.next();
			if (lixo.isVisivel() && boundsVassoura.intersects(lixo.getBounds())) {
				System.out.println("Vassoura limpou um lixo!");
				lixo.setVisivel(false);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		if (jogoRodando) {
			for (Inimigo lixo : lixos) {
				lixo.draw(g2);
			}
			personagem.draw(g2);
			vassoura.draw(g2);
		} else {
			g2.setColor(Color.RED);
			g2.setFont(g2.getFont().deriveFont(50f));
			String gameOverMsg = "GAME OVER";
			int strWidth = g2.getFontMetrics().stringWidth(gameOverMsg);
			g2.drawString(gameOverMsg, (LARGURA_JANELA - strWidth) / 2, ALTURA_JANELA / 2);
		}
		g2.dispose();
	}

	private void mostrarGameOver(Graphics g) {
		if (g == null)
			return;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.RED);
		g2.setFont(g2.getFont().deriveFont(50f));
		String gameOverMsg = "GAME OVER";
		int strWidth = g2.getFontMetrics().stringWidth(gameOverMsg);
		g2.drawString(gameOverMsg, (LARGURA_JANELA - strWidth) / 2, ALTURA_JANELA / 2);
		g2.dispose();
	}
}