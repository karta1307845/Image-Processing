package final_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class GrayFrame extends BasicFrame {

	GrayFrame() {
		super("灰階");
		btnFunction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					int[][] gray = Util.gray(data);
					BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							int value = gray[i][j];
							int color = Util.getColor(value, value, value);
							newImg.setRGB(j, i, color);
						}
					}
					imagePanel2.paintComponent(imagePanel2.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
