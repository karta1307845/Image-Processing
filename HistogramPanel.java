package final_project;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class HistogramPanel extends JPanel {

	public void paintComponent(Graphics g, int[][] gray, int thresh) {
		super.paintComponent(g);

		int height = getHeight();
		int[] count = new int[256];
		int max = 0;
		double scale = 1.0;

		for (int[] i : gray) {
			for (int j : i) {
				count[j]++;
			}
		}
		for (int i = 0; i < 256; i++) {
			int value = count[i];
			if (value > max) {
				max = value;
			}
		}
		if (max > height) {
			scale = max / height;
		}

		g.setColor(Color.gray);
		for (int i = 0; i < 256; i++) {
			int value = count[i];
			value = (int) Math.round(value / scale);
			g.drawLine(i, height - 1, i, height - 1 - value);
		}
		g.setColor(Color.red);
		g.drawLine(thresh, 0, thresh, height - 1);
	}
}
