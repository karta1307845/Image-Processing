package final_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class SharpFrame extends BasicFrame{
	
	SharpFrame() {
		super("Sharp");
		btnFunction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
					for (int i = 0; i < height; i++) {
						for (int j = 0; j < width; j++) {
							int[] rgb = Util.getHighPassData(j, i, data);
							int color = Util.getColor(rgb[0], rgb[1], rgb[2]);
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
