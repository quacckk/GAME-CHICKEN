package polutionMan;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class item {
	private int x, y;
	private int largura, altura;
	private boolean ativa;
	private BufferedImage imagemItem;

	public item(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
		this.ativa = false;
		this.x = -1000;
		this.y = -1000;

		carregarImagemItem();
	}

	private void carregarImagemItem() {
		try {
			imagemItem = ImageIO.read(getClass().getResourceAsStream("/res/broom.png"));

			if (imagemItem == null) {
				System.err.println("ERRO NA IMAGEM");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("deu algum erro ai");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println("Erro no argumento ao carregar imagem: " + e.getMessage());
		}
	}

	public void update(personagem personagem) {
		if (ativa && personagem != null) {
			this.x = personagem.getX() + personagem.getLargura() - (largura / 3);
			this.y = personagem.getY() + (personagem.getAltura() / 2) - (this.altura / 2);
		}
	}

	public void draw(Graphics2D g2) {
		if (ativa) {
			if (imagemItem != null) {
				g2.drawImage(imagemItem, x, y, largura, altura, null);
			} else {
				g2.setColor(Color.ORANGE);
				g2.fillRect(x, y, largura, altura);
				g2.setColor(Color.RED);
				g2.drawString("Img Falhou", x, y + altura / 2);
			}
		}
	}

	public boolean isAtiva() {
		return ativa;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, largura, altura);
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
		if (!ativa) {
			this.x = -1000;
			this.y = -1000;
		}
	}

	public void setPosicao(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
