package polutionMan;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Lixo {

    private int x, y;
    private int largura, altura;
    private boolean ativo;
    private int velocidade;
    private BufferedImage imagem;

    public Lixo(int x, int y, int largura, int altura, int velocidade, String caminhoImagem) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.velocidade = velocidade;
        this.ativo = true;
        carregarImagem(caminhoImagem);
    }

    private void carregarImagem(String caminho) {
        try {
            imagem = ImageIO.read(getClass().getResourceAsStream(caminho));
        } catch (IOException | NullPointerException e) {
            System.err.println("Erro ao carregar imagem do lixo: " + e.getMessage());
        }
    }

    public void update() {
    	if(!ativo) {
    		return;
    	}
        x -= velocidade;

        if (x + largura < 0) {
            x = Game.LARGURA_JANELA + (int)(Math.random()*200);
            y = (int)(Math.random()* (Game.ALTURA_JANELA - altura));
            ativo = true;
        }
    }

    public void draw(Graphics2D g2) {
        if (ativo ) {
            g2.drawImage(imagem, x, y, largura, altura, null);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

