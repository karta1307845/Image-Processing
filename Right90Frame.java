package final_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

public class Right90Frame extends BasicFrame{
	Right90Frame() {
		super("右旋90度");
		btnFunction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (img != null) {
					BufferedImage newImg = Util.rotateRight(data);
					imagePanel2.paintComponent(imagePanel2.getGraphics(), newImg);
				} else {
					JOptionPane.showMessageDialog(null, "Show an image before processing.");
				}
			}

		});
	}
}
