package polutionMan;
import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Chicken Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Tela());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

}
