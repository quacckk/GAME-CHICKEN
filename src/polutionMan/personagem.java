package polutionMan;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Personagem {

	private int x, y;
	private int velocidade;
	private int largura, altura;
	private int yLimiteParaPegarVassoura;
	private int larguraJanela, alturaJanela;

	public Personagem(int xInicial, int yInicial, int largura, int altura, int velocidade, int yLimiteVassoura,
			int larguraJanela, int alturaJanela) {
		this.x = xInicial;
		this.y = yInicial;
		this.largura = largura;
		this.altura = altura;
		this.velocidade = velocidade;
		this.yLimiteParaPegarVassoura = yLimiteVassoura;
		this.larguraJanela = larguraJanela;
		this.alturaJanela = alturaJanela;
	}

	public void update(ControleTecla controleTecla) {
		int novoX = x;
		int novoY = y;

		if (controleTecla.upPressed) {
			novoY -= velocidade;
		}
		if (controleTecla.downPressed) {
			novoY += velocidade;
		}
		if (controleTecla.leftPressed) {
			novoX -= velocidade;
		}
		if (controleTecla.rightPressed) {
			novoX += velocidade;
		}

		if (novoX >= 0 && novoX + largura <= larguraJanela) {
			x = novoX;
		}
		if (novoY >= 0 && novoY + altura <= alturaJanela) {
			y = novoY;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.fillRect(x, y, largura, altura);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, largura, altura);
	}

	public boolean chegouAoLocalDaVassoura() {
		return this.y <= yLimiteParaPegarVassoura;
	}

	public void setPosicao(int x, int y) {
		this.x = x;
		this.y = y;
	}
}