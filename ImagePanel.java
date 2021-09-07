package final_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	public void paintComponent(Graphics g, BufferedImage img) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
}
