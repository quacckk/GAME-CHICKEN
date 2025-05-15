package polutionMan;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

public class Inimigo {

	private int x, y;
	private int largura, altura;
	private boolean visivel;
	private int velocidade;

	public Inimigo(int x, int y, int largura, int altura, int velocidade) {
		this.x = x;
		this.y = y;
		this.largura = largura;
		this.altura = altura;
		this.velocidade = velocidade;
		this.visivel = true;
	}

	public void update() {
	}

	public void draw(Graphics2D g2) {
		if (visivel) {
			g2.setColor(Color.GRAY);
			g2.fillRect(x, y, largura, altura);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisivel() {
		return visivel;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, largura, altura);
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

	public void setPosicao(int x, int y) {
		this.x = x;
		this.y = y;
	}
}