package polutionMan;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;


public class personagem {
	private int x, y;
	private int velocidade;
	private int largura, altura;
	private int yLimiteParaPegarItem;
	
	public personagem(int xInicial, int yInicial, int largura, int altura, int velocidade, int yLimiteVassoura,
			int larguraJanela, int alturaJanela) {
		this.x = xInicial;
		this.y = yInicial;
		this.largura = largura;
		this.altura = altura;
		this.velocidade = velocidade;
		this.yLimiteParaPegarItem = yLimiteVassoura;
		this.larguraJanela = larguraJanela;
		this.alturaJanela = alturaJanela;
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

	public boolean chegouAoLocalDaItem() {
		return this.y <= yLimiteParaPegarItem;
	}

	public void setPosicao(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
